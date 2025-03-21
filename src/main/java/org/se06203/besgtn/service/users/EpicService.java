package org.se06203.besgtn.service.users;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.dto.request.epic.CreateEpicRequest;
import org.se06203.besgtn.dto.response.tasks.GetAllTaskResponse;
import org.se06203.besgtn.persistence.entity.TaskManagement;
import org.se06203.besgtn.persistence.repository.TaskManagementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EpicService {

    private final TaskManagementRepository taskManagementRepository;

    @Transactional
    public String createEpic(CreateEpicRequest request) {


        return null;
    }

    @Transactional
    public List<GetAllTaskResponse> getALlEpicByType(String type) {
        return getAllEpic(type, taskManagementRepository);
    }

    static List<GetAllTaskResponse> getAllEpic(String type, TaskManagementRepository taskManagementRepository) {
        List<TaskManagement> epicAll;
        if(type.isEmpty()){
            epicAll = taskManagementRepository.findAll();
        }
        else {
            epicAll = taskManagementRepository.findAllByType(type);
        }
        return epicAll.stream()
                .map(epic -> GetAllTaskResponse.builder()
                        .epicName(epic.getNameEpic())
                        .description(epic.getDescription())
                        .build()).toList();
    }
}

