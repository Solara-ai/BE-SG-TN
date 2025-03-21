package org.se06203.besgtn.controller.user.task;

import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.dto.request.CreateTaskRequest;
import org.se06203.besgtn.dto.response.tasks.GetTotalTaskAndPlanResponse;
import org.se06203.besgtn.persistence.repository.TaskRepository;
import org.se06203.besgtn.service.users.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController implements TaskApi {
    public final TaskRepository taskRepository;
    public final TaskService taskService;
    private final ResponseFactory responseFactory;


    @Override
    public ResponseEntity<BaseResponse> createTask(CreateTaskRequest request) {
        return null;
    }

//    @Override
//    public ResponseEntity<BaseDataResponse<List<GetAllTaskResponse>>> getTasksByType(String type) {
//        return responseFactory.success(HttpStatus.OK,taskService.getALlTaskByType(type));
//    }

    @Override
    public ResponseEntity<BaseDataResponse<GetTotalTaskAndPlanResponse>> getTotalTaskAndPlans(String status) {
        return responseFactory.success(HttpStatus.OK,taskService.getTotalTaskAndPlansByStatus(status));
    }
}
