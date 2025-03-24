package org.se06203.besgtn.utils.mapper;

import org.mapstruct.*;
import org.se06203.besgtn.dto.request.ScheduleDto.GetDateTime;
import org.se06203.besgtn.dto.request.ScheduleDto.GetListScheduleRes;
import org.se06203.besgtn.persistence.entity.ChildSchedule;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChildScheduleMapper {
    @Mapping(target = "date", source = "date", qualifiedByName = "stringToLocalDate")
    GetDateTime mapSchedulesToGetDateTime(ChildSchedule schedules);

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String date) {
        return date != null ? LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    @Mapping(target = "eventId", source = "id")
    GetListScheduleRes mapSchedulesToGetListScheduleRes(ChildSchedule childSchedule);
}
