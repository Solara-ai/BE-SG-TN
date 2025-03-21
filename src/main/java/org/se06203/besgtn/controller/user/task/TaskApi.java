package org.se06203.besgtn.controller.user.task;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.dto.request.CreateTaskRequest;
import org.se06203.besgtn.dto.response.tasks.GetTotalTaskAndPlanResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/tasks")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Tasks", description = "Api for user")
public interface TaskApi {


    @PostMapping("/create")
    ResponseEntity<BaseResponse> createTask(@RequestBody CreateTaskRequest request);


//    @GetMapping("/view-tasks")
//    ResponseEntity<BaseDataResponse<List<GetAllTaskResponse>>> getTasksByType(
//            @RequestParam(value = "type", required = false) String type);

    @GetMapping("/total")
    ResponseEntity<BaseDataResponse<GetTotalTaskAndPlanResponse>> getTotalTaskAndPlans(@RequestParam String status);
}
