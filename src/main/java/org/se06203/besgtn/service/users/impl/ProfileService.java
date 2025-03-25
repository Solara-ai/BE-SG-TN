package org.se06203.besgtn.service.users.impl;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.config.security.SecurityUtils;
import org.se06203.besgtn.dto.response.ProfileResponse;
import org.se06203.besgtn.persistence.repository.UserRepository;
import org.se06203.besgtn.utils.mapper.ProfileMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {
    private UserRepository userRepository;
    private ProfileMapper profileMapper;

    @org.springframework.beans.factory.annotation.Autowired
    public ProfileService(UserRepository userRepository, ProfileMapper profileMapper) {
        this.userRepository = userRepository;
        this.profileMapper = profileMapper;
    }

    public ProfileResponse getProfile() {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.USER_NOT_FOUND));

        return profileMapper.mapToProfileResponse(user);
    }
}
