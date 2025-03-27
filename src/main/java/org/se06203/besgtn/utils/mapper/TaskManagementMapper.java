package org.se06203.besgtn.utils.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.se06203.besgtn.dto.response.epic.GetEpicRes;
import org.se06203.besgtn.persistence.entity.TaskManagement;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskManagementMapper {
    GetEpicRes mapTaskManagementToGetEpicRes(TaskManagement taskManagement);
}
