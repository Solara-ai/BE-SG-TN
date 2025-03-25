package org.se06203.besgtn.utils.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.se06203.besgtn.dto.request.scheduleDto.*;
import org.se06203.besgtn.dto.response.scheduleDto.GetDetailScheduleRes;
import org.se06203.besgtn.persistence.entity.Schedules;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {

    Schedules mapInsertScheduleReqToSchedules(InsertScheduleReq req);

    Schedules mapUpdateScheduleReqToSchedules(UpdateScheduleReq req);

    @Mapping(target = "scheduleId", source = "id")
    @Mapping(target = "eventName", source = "name")
    @Mapping(target = "endDate", source = "repeatEndDate")
    GetDetailScheduleRes mapSchedulesToGetDetailSchedule(Schedules schedule);
}
