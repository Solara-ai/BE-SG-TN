package org.se06203.besgtn.service.users;

import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.security.SecurityUtils;
import org.se06203.besgtn.dto.request.CreateEventRequest;
import org.se06203.besgtn.dto.response.ViewSchedulesEventResponse;
import org.se06203.besgtn.persistence.entity.Schedules;
import org.se06203.besgtn.persistence.entity.Users;
import org.se06203.besgtn.persistence.repository.ScheduleRepository;
import org.se06203.besgtn.persistence.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor

public class SchedulesService {

    public  final ScheduleRepository scheduleRepository;

//    @Transactional
//    public Stream<ViewSchedulesEventResponse> viewSchedulesEvent() {
//        var userId = SecurityUtils.getAuthenticatedUser().getId();
//        LocalTime currentTime = LocalTime.now();
//
//        List<Schedules> schedules = scheduleRepository.findByUserId(userId);
//        var startTime =
//        return schedules
//                .stream()
//                .filter(schedule -> schedule.getStartTime().isAfter(currentTime))
//                .map(schedule -> {
//                    ViewSchedulesEventResponse response = new ViewSchedulesEventResponse();
//                    response.setTitle(schedule.getName());
//                    response.setDescription(schedule.getDescription());
//                    response.setStartTime(schedule.getStartTime());
//                    response.setEndTime(schedule.getEndTime());
//                    return response;
//                });
//    }
//
//
//    @Transactional
//    public String createSchedule(CreateEventRequest request) {
//        var userId = SecurityUtils.getAuthenticatedUser().getId();
//
//        Schedules schedule = scheduleRepository.save(Schedules.builder()
//                        .userId(userId)
//                        .name(request.getName())
//                        .description(request.getDescription())
//                        .remindMe(request.isRemindMe())
//                        .repeat(request.getRepeat())
//                        .date(request.getDate())
//                        .categoryId(request.getCategoryId())
//                .build());
//        return schedule.getId();
//    }
}
