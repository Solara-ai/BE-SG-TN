package org.se06203.besgtn.controller.user.schedules;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.dto.request.CreateEventRequest;
import org.se06203.besgtn.dto.response.ViewSchedulesEventResponse;
import org.se06203.besgtn.service.users.SchedulesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SchedulesController implements SchedulesApi {

    public final SchedulesService schedulesService;
    public final ResponseFactory responseFactory;

//    @Override
//    public ResponseEntity<BaseDataResponse<Stream<ViewSchedulesEventResponse>>> getSchedules() {
//        return responseFactory.success(HttpStatus.OK,schedulesService.viewSchedulesEvent());
//    }
//
//    @Override
//    public ResponseEntity<BaseResponse> createSchedule(CreateEventRequest request) {
//        return responseFactory.success(HttpStatus.OK,schedulesService.createSchedule(request));
//    }
}
