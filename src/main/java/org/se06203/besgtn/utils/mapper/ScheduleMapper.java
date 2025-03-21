package org.se06203.besgtn.utils.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.se06203.besgtn.dto.request.ScheduleDto.InsertScheduleReq;
import org.se06203.besgtn.persistence.entity.Schedules;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {

    Schedules mapToSchedules(InsertScheduleReq req);
}
