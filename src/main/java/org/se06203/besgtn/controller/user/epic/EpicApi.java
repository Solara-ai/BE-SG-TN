package org.se06203.besgtn.controller.user.epic;


import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.dto.request.epic.CreateEpicRequest;
import org.se06203.besgtn.dto.response.tasks.GetAllTaskResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/epic")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Epic", description = "Api for user")
public interface EpicApi {

    @PostMapping("/create")
    ResponseEntity<BaseResponse> createEpic(@RequestBody CreateEpicRequest request);

    @GetMapping("/view-epic")
    ResponseEntity<BaseDataResponse<List<GetAllTaskResponse>>> getEpicsByType(
            @RequestParam(value = "type", required = false) String type);

    @PutMapping("/update")
    ResponseEntity<BaseResponse> updateEpic(@RequestBody CreateEpicRequest request);

    @DeleteMapping("/delete")
    ResponseEntity<BaseResponse> deleteEpic();

}
