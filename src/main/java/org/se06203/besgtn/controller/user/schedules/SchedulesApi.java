package org.se06203.besgtn.controller.user.schedules;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.dto.request.CreateEventRequest;
import org.se06203.besgtn.dto.response.ViewSchedulesEventResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("/users/schedules")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Schedules", description = "Api for user")
public interface SchedulesApi {
//    @GetMapping
//    ResponseEntity<BaseDataResponse<Stream<ViewSchedulesEventResponse>>> getSchedules();
//
//
//    @PostMapping
//    ResponseEntity<BaseResponse> createSchedule(@RequestBody CreateEventRequest request);
}
