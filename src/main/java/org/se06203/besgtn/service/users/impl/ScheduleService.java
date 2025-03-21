package org.se06203.besgtn.service.users.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.security.SecurityUtils;
import org.se06203.besgtn.dto.request.ScheduleDto.InsertScheduleReq;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.persistence.entity.ChildSchedule;
import org.se06203.besgtn.persistence.repository.ScheduleRepository;
import org.se06203.besgtn.utils.mapper.ScheduleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.se06203.besgtn.utils.ConvertDateTime.convertStringToDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    @Transactional
    public void createSchedule(InsertScheduleReq req) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var schedule = scheduleMapper.mapToSchedules(req);

        var nextDate = convertStringToDate(req.getDate());
        var repeatEndDate = convertStringToDate(req.getRepeatEndDate());

        List<ChildSchedule> child = new ArrayList<>();
        while (nextDate.isBefore(repeatEndDate)) {
            child.add(ChildSchedule.builder()
                    .title(req.getName())
                    .weekday(nextDate.getDayOfWeek().toString())
                    .date(nextDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .startTime(req.getStartTime())
                    .endTime(req.getEndTime())
                    .build());

            nextDate = switch (req.getRepeat()) {
                case DAILY -> nextDate.plusDays(1);
                case WEEKLY -> nextDate.plusWeeks(1);
                case MONTHLY -> nextDate.plusMonths(1);
                case YEARLY -> nextDate.plusYears(1);
                default -> throw new BaseRuntimeException(ErrorCodeMsg.REPEAT_NOT_FOUND);
            };
        }
        schedule.setChildSchedules(child);
        schedule.setUserId(userId);

        scheduleRepository.save(schedule);
    }
}
