package org.se06203.besgtn.controller.user.schedules;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.PagedData;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.dto.request.scheduleDto.*;
import org.se06203.besgtn.dto.response.scheduleDto.GetDateTimeRes;
import org.se06203.besgtn.dto.response.scheduleDto.GetDetailScheduleRes;
import org.se06203.besgtn.dto.response.scheduleDto.GetListScheduleRes;
import org.se06203.besgtn.service.users.impl.UsersScheduleService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ScheduleController implements ScheduleApi{

    private final ResponseFactory responseFactory;
    private final UsersScheduleService usersScheduleService;

    @Override
    public ResponseEntity<BaseResponse> createSchedule(InsertScheduleReq req) {
        usersScheduleService.createSchedule(req);
        return responseFactory.success(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BaseResponse> updateSchedule(String ScheduleId, UpdateScheduleReq req) {
        usersScheduleService.updateSchedule(ScheduleId, req);
        return responseFactory.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseDataResponse<List<GetListScheduleRes>>> getListSchedulesByDate(String date) {
        return responseFactory.success(HttpStatus.OK, usersScheduleService.getListSchedulesByDate(date));
    }

    @Override
    public ResponseEntity<BaseDataResponse<List<GetDateTimeRes>>> getListSchedules(int year, int month) {
        var yearMonth = YearMonth.of(year, month);
        return responseFactory.success(HttpStatus.OK, usersScheduleService.getListSchedules(yearMonth));
    }

    @Override
    public ResponseEntity<BaseDataResponse<GetDetailScheduleRes>> getScheduleDetailByEventId(String eventId) {
        return responseFactory.success(HttpStatus.OK, usersScheduleService.getScheduleDetailByEventId(eventId));
    }

    @Override
    public ResponseEntity<BaseResponse> deleteScheduleByEventId(String eventId) {
        usersScheduleService.deleteScheduleByEventId(eventId);
        return responseFactory.success(HttpStatus.NO_CONTENT);
    }
}
