package org.se06203.besgtn.controller.user.schedules;

import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.se06203.besgtn.config.response.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.se06203.besgtn.dto.request.ScheduleDto.InsertScheduleReq;

@RestController
@RequestMapping("/users/schedules")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Schedules", description = "Api for user Schedules")
public interface ScheduleApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "Schedules", summary = "Create Schedule.")
    ResponseEntity<BaseResponse> createSchedule(InsertScheduleReq req);
}
