package org.se06203.besgtn.service.users.impl;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.config.security.SecurityUtils;
import org.se06203.besgtn.dto.request.ProfileRequest;
import org.se06203.besgtn.dto.response.ProfileResponse;
import org.se06203.besgtn.persistence.repository.UserRepository;
import org.se06203.besgtn.utils.mapper.ProfileMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersProfileService {
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;
    private final PasswordEncoder passwordEncoder;

    public ProfileResponse getProfile() {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.USER_NOT_FOUND));

        return profileMapper.mapToProfileResponse(user);
    }

    public void updateProfile(ProfileRequest profileRequest) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.USER_NOT_FOUND));

        user.setFullName(profileRequest.getFullName());
        user.setPhone(profileRequest.getPhone());
        user.setBirthday(profileRequest.getBirthday());
        user.setGender(profileRequest.getGender());
        user.setHobbies(profileRequest.getHobbies());
        user.setOccupation(profileRequest.getOccupation());
        user.setPassword(passwordEncoder.encode(profileRequest.getPassword()));

        userRepository.save(user);
    }
}
