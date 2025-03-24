package org.se06203.besgtn.controller.user.schedules;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.dto.request.ScheduleDto.InsertScheduleReq;
import org.se06203.besgtn.dto.request.ScheduleDto.UpdateScheduleReq;
import org.se06203.besgtn.service.users.impl.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ScheduleController implements ScheduleApi{

    private final ResponseFactory responseFactory;
    private final ScheduleService scheduleService;

    @Override
    public ResponseEntity<BaseResponse> createSchedule(InsertScheduleReq req) {
        scheduleService.createSchedule(req);
        return responseFactory.success(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BaseResponse> updateSchedule(String ScheduleId, UpdateScheduleReq req) {
        scheduleService.updateSchedule(ScheduleId, req);
        return responseFactory.success(HttpStatus.OK);
    }
}
