package org.se06203.besgtn.utils.mapper;

import org.mapstruct.*;
import org.se06203.besgtn.dto.request.CreateUserRequest;
import org.se06203.besgtn.dto.response.ProfileResponse;
import org.se06203.besgtn.persistence.entity.Users;
import org.se06203.besgtn.utils.Constants;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {

    ProfileResponse mapToProfileResponse(Users user);

    @Mapping(target = "roles", source = "authority")
    Users mapToCreateUserRequest(CreateUserRequest user);
}
