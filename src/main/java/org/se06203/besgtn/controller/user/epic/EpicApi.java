package org.se06203.besgtn.controller.user.epic;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.dto.request.tasks.CreateTaskRequest;
import org.se06203.besgtn.dto.request.epic.CreateEpicRequest;
import org.se06203.besgtn.dto.response.epic.GetEpicRes;
import org.se06203.besgtn.dto.response.tasks.GetTaskDetail;
import org.se06203.besgtn.dto.response.tasks.GetTaskRes;
import org.se06203.besgtn.dto.response.tasks.GetTotalTaskAndPlanResponse;
import org.se06203.besgtn.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/epic")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Epic", description = "Api for user")
public interface EpicApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "Epic", summary = "Create Epic")
    ResponseEntity<BaseResponse> createEpic(@RequestBody CreateEpicRequest request);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Epic", summary = "View Epic")
    ResponseEntity<BaseDataResponse<List<GetEpicRes>>> getEpicsByType(
            @RequestParam(value = "type", required = false) String type);

    @GetMapping("/{epicId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Epic", summary = "View Detail Epic")
    ResponseEntity<BaseDataResponse<GetEpicRes>> getEpicDetail(@PathVariable(value = "epicId") String id);

    @PutMapping("/{epicId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Epic", summary = "Update Epic")
    ResponseEntity<BaseResponse> updateEpic(@RequestBody CreateEpicRequest request,
                                            @PathVariable(value = "epicId") String id);

    @DeleteMapping("/{epicId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Epic", summary = "Delete Epic")
    ResponseEntity<BaseResponse> deleteEpic(@PathVariable(value = "epicId") String id);

    @PostMapping("/{epicId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "Epic", summary = "Create Task")
    ResponseEntity<BaseResponse> createTask(
                                            @RequestBody CreateTaskRequest request);

    @GetMapping("/{epicId}/tasks")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Epic", summary = "View Task")
    ResponseEntity<BaseDataResponse<GetTaskRes>> getTasksByEpic(@PathVariable(value = "epicId") String id);

    @GetMapping("/{epicId}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Epic", summary = "View Detail Task")
    ResponseEntity<BaseDataResponse<GetTaskDetail>> getTaskDetail(@PathVariable(value = "epicId") String epicId,
                                                                  @PathVariable(value = "taskId") String taskId);

    @PutMapping("/{epicId}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Epic", summary = "Update Task")
    ResponseEntity<BaseResponse> updateTask(@PathVariable(value = "epicId") String epicId,
                                            @PathVariable(value = "taskId") String taskId,
                                            @RequestBody CreateTaskRequest request);

    @PutMapping("/{epicId}/tasks/{taskId}/status")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Epic", summary = "Update Task Status")
    ResponseEntity<BaseResponse> updateTaskStatus(@PathVariable(value = "epicId") String epicId,
                                                  @PathVariable(value = "taskId") String taskId,
                                                  @RequestParam(value = "status") Constants.TypeTask status);

    @DeleteMapping("/{epicId}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Epic", summary = "Delete Task")
    ResponseEntity<BaseResponse> deleteTask(@PathVariable(value = "epicId") String epicId,
                                            @PathVariable(value = "taskId") String taskId);

    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Epic", summary = "View Total Task and Plan")
    ResponseEntity<BaseDataResponse<GetTotalTaskAndPlanResponse>> getTotalTaskAndPlans(@RequestParam String date);
}
