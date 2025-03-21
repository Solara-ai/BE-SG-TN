package org.se06203.besgtn.service.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.dto.exception.ErrorCodeMsg;
import org.se06203.besgtn.dto.response.tasks.GetAllTaskResponse;
import org.se06203.besgtn.dto.response.tasks.GetTotalTaskAndPlanResponse;
import org.se06203.besgtn.persistence.entity.TaskManagement;
import org.se06203.besgtn.persistence.repository.PlanRepository;
import org.se06203.besgtn.persistence.repository.TaskManagementRepository;
import org.se06203.besgtn.persistence.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.se06203.besgtn.service.users.EpicService.getAllEpic;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final PlanRepository planRepository;
    private final TaskManagementRepository taskManagementRepository;


    @Transactional
    public GetTotalTaskAndPlanResponse getTotalTaskAndPlansByStatus(String status) {
        long count = taskRepository.countByStatus(status);
        long count2 = planRepository.countByStatus(status);
        if (count == 0 || count2 == 0) {
            throw new BaseRuntimeException(ErrorCodeMsg.NOT_FOUND);
        }
        else {
            return GetTotalTaskAndPlanResponse.builder()
                    .totalTasks(String.valueOf(count))
                    .totalPlans(String.valueOf(count2))
                    .build();
        }
    }

//    @Transactional
//    public List<GetAllTaskResponse> getALlTaskByType(String type) {
//        return getAllEpic(type, taskManagementRepository);
//    }

}
