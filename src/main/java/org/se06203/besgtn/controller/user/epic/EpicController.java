package org.se06203.besgtn.controller.user.epic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.dto.request.epic.CreateEpicRequest;
import org.se06203.besgtn.dto.request.tasks.CreateTaskRequest;
import org.se06203.besgtn.dto.response.epic.GetEpicRes;
import org.se06203.besgtn.dto.response.tasks.GetTaskDetail;
import org.se06203.besgtn.dto.response.tasks.GetTaskRes;
import org.se06203.besgtn.dto.response.tasks.GetTotalTaskAndPlanResponse;
import org.se06203.besgtn.service.users.impl.EpicService;
import org.se06203.besgtn.service.users.impl.TaskService;
import org.se06203.besgtn.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EpicController implements EpicApi {

    private final EpicService epicService;

    private final ResponseFactory responseFactory;
    private final TaskService taskService;

    @Override
    public ResponseEntity<BaseResponse> createEpic(CreateEpicRequest request) {
        epicService.createEpic(request);
        return responseFactory.success(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BaseDataResponse<List<GetEpicRes>>> getEpicsByType(String type) {
        return responseFactory.success(HttpStatus.OK, epicService.getALlEpicByType(type));
    }

    @Override
    public ResponseEntity<BaseDataResponse<GetEpicRes>> getEpicDetail(String id) {
        return responseFactory.success(HttpStatus.OK, epicService.getEpicDetail(id));
    }

    @Override
    public ResponseEntity<BaseResponse> updateEpic(CreateEpicRequest request, String id) {
        epicService.updateEpicRequest(id, request);
        return responseFactory.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse> deleteEpic(String id) {
        epicService.deleteEpic(id);
        return responseFactory.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse> createTask( CreateTaskRequest request) {
        taskService.createTask( request);
        return responseFactory.success(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BaseDataResponse<GetTaskRes>> getTasksByEpic(String id) {
        return responseFactory.success(HttpStatus.OK, taskService.getAllTaskByEpic(id));
    }

    @Override
    public ResponseEntity<BaseDataResponse<GetTaskDetail>> getTaskDetail(String epicId, String taskId) {
        return responseFactory.success(HttpStatus.OK, taskService.getTaskDetail(epicId, taskId));
    }

    @Override
    public ResponseEntity<BaseResponse> updateTask(String epicId, String taskId, CreateTaskRequest request) {
        taskService.updateTask(epicId, taskId, request);
        return responseFactory.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse> updateTaskStatus(String epicId, String taskId, Constants.TypeTask status) {
        taskService.updateTaskStatus(epicId, taskId, status);
        return responseFactory.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse> deleteTask(String epicId, String taskId) {
        taskService.deleteTask(epicId, taskId);
        return responseFactory.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseDataResponse<GetTotalTaskAndPlanResponse>> getTotalTaskAndPlans(String date) {
        return responseFactory.success(HttpStatus.OK, taskService.getTotalTaskAndPlansByStatus(date));
    }
}
