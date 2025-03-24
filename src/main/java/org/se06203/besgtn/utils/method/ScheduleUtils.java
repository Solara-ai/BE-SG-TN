package org.se06203.besgtn.utils.method;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.dto.request.ScheduleDto.UpdateScheduleReq;
import org.se06203.besgtn.persistence.entity.ChildSchedule;
import org.se06203.besgtn.persistence.entity.ScheduleException;
import org.se06203.besgtn.persistence.entity.Schedules;
import org.se06203.besgtn.utils.Constants;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduleUtils {
    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalDate incrementDate(LocalDate date, Constants.RepeatType repeatType, LocalDate repeatEndDate) {
        return switch (repeatType) {
            case DAILY -> date.plusDays(1);
            case WEEKLY -> date.plusWeeks(1);
            case MONTHLY -> date.plusMonths(1);
            case YEARLY -> date.plusYears(1);
            case NONE -> repeatEndDate;
        };
    }

    public static boolean isExceptionDate(Schedules schedule, String formattedDate) {
        return schedule.getExceptions() != null && schedule.getExceptions().stream()
                .map(ScheduleException::getExceptionDate)
                .anyMatch(exceptionDate -> exceptionDate.equals(formattedDate));
    }

    public static ChildSchedule createChildSchedule(UpdateScheduleReq req, LocalDate date) {
        return ChildSchedule.builder()
                .title(req.getName())
                .weekday(date.getDayOfWeek().toString())
                .date(formatDate(date))
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .build();
    }

    public static ScheduleException createScheduleException(UpdateScheduleReq req) {
        return ScheduleException.builder()
                .exceptionDate(req.getDate())
                .statTime(req.getStartTime())
                .endTime(req.getEndTime())
                .build();
    }
}