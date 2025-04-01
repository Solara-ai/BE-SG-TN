package org.se06203.besgtn.utils.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.se06203.besgtn.dto.request.CreateUserRequest;
import org.se06203.besgtn.dto.response.ProfileResponse;
import org.se06203.besgtn.persistence.entity.Users;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {

    ProfileResponse mapToProfileResponse(Users user);

    Users mapToCreateUserRequest(CreateUserRequest user);
}
