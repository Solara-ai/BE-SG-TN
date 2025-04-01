package org.se06203.besgtn.service.users.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.config.security.SecurityUtils;
import org.se06203.besgtn.dto.request.epic.CreateEpicRequest;
import org.se06203.besgtn.dto.response.epic.GetEpicRes;
import org.se06203.besgtn.persistence.entity.TaskManagement;
import org.se06203.besgtn.persistence.repository.TaskManagementRepository;
import org.se06203.besgtn.utils.mapper.TaskManagementMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class EpicService {

    private final TaskManagementRepository taskManagementRepository;
    private final TaskManagementMapper taskManagementMapper;

    @Transactional
    public void createEpic(CreateEpicRequest request) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();

        taskManagementRepository.save(TaskManagement.builder()
                .userId(userId)
                .nameEpic(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .build());
    }

    public List<GetEpicRes> getALlEpicByType(String type) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        List<TaskManagement> epicAll;
        if (Objects.isNull(type) || type.isBlank()) {
            epicAll = taskManagementRepository.findAllActiveEpicsByUserId(userId);
        } else {
            epicAll = taskManagementRepository.findAllByTypeAndUserId(type, userId);
        }
        return epicAll.stream()
                .map(epic -> GetEpicRes.builder()
                        .id(epic.getId())
                        .nameEpic(epic.getNameEpic())
                        .description(epic.getDescription())
                        .type(epic.getType())
                        .build()).toList();
    }

    @Transactional
    public void deleteEpic(String id) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var epic = taskManagementRepository.findActiveEpicByIdAndUserId(id, userId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.EPIC_NOT_FOUND));

        epic.setDeletedAt(Instant.now());

        taskManagementRepository.save(epic);
    }

    @Transactional
    public void updateEpicRequest(String id, CreateEpicRequest request) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var epic = taskManagementRepository.findActiveEpicByIdAndUserId(id, userId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.EPIC_NOT_FOUND));

        epic.setUserId(userId);
        epic.setNameEpic(request.getName());
        epic.setDescription(request.getDescription());
        epic.setType(request.getType());
        epic.setUpdatedAt(Instant.now());

        taskManagementRepository.save(epic);
    }

    public GetEpicRes getEpicDetail(String id) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();

        var epic = taskManagementRepository.findActiveEpicByIdAndUserId(id, userId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.EPIC_NOT_FOUND));

        return taskManagementMapper.mapTaskManagementToGetEpicRes(epic);
    }
}

