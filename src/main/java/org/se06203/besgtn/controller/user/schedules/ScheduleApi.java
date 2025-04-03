package org.se06203.besgtn.controller.user.schedules;

import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.dto.request.AddEventReq;
import org.se06203.besgtn.dto.request.scheduleDto.*;
import org.se06203.besgtn.dto.response.scheduleDto.GetDateTimeRes;
import org.se06203.besgtn.dto.response.scheduleDto.GetDetailScheduleRes;
import org.se06203.besgtn.dto.response.scheduleDto.GetListScheduleRes;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.se06203.besgtn.config.response.BaseResponse;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/users/schedules")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Schedules", description = "Api for user Schedules")
public interface ScheduleApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "Schedules", summary = "Create Schedule.")
    ResponseEntity<BaseResponse> createSchedule(@RequestBody InsertScheduleReq req);

    @PutMapping("/{ScheduleId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Schedules", summary = "Update Schedule.")
    ResponseEntity<BaseResponse> updateSchedule(@PathVariable String ScheduleId, @RequestBody UpdateScheduleReq req);

    @GetMapping("/date")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Schedules", summary = "Get List Schedules By Date.")
    ResponseEntity<BaseDataResponse<List<GetListScheduleRes>>> getListSchedulesByDate(
            @RequestParam(value = "date") String date);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Schedules", summary = "Get List Schedules.")
    ResponseEntity<BaseDataResponse<List<GetDateTimeRes>>> getListSchedules(@RequestParam int year,
                                                                            @RequestParam int month);

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Schedules", summary = "Get Schedule Detail By Event Id.")
    ResponseEntity<BaseDataResponse<GetDetailScheduleRes>> getScheduleDetailByEventId(@PathVariable String eventId);

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(tags = "Schedules", summary = "Delete Schedule By Event Id.")
    ResponseEntity<BaseResponse> deleteScheduleByEventId(@PathVariable String eventId);

    @PostMapping("/add-event")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "Schedules", summary = "Add Event to ChatBot.")
    ResponseEntity<BaseResponse> addEventToChatBot(@RequestBody AddEventReq req);
}
