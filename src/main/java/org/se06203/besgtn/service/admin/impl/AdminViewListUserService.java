package org.se06203.besgtn.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.dto.admin.response.GetListUsersResponse;
import org.se06203.besgtn.dto.request.CreateUserRequest;
import org.se06203.besgtn.persistence.repository.UserRepository;
import org.se06203.besgtn.utils.Constants;
import org.se06203.besgtn.utils.mapper.ProfileMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminViewListUserService {


    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;

    @Transactional
    public List<GetListUsersResponse > getListUser(){
        var users = userRepository.findAll();
        return users.stream()
                .map(user -> GetListUsersResponse.builder()
                        .id(user.getId())
                        .name(user.getFullName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .createdAt(user.getCreatedAt())
                        .birthday(user.getBirthday())
                        .gender(user.getGender())
                        .build())
                .toList();
    }

    public void createUser(CreateUserRequest req) {
        userRepository.findByEmail(req.getEmail()).ifPresentOrElse(
                user -> {
                    throw new BaseRuntimeException(ErrorCodeMsg.USER_ALREADY_EXIST);
                },
                () -> userRepository.save(profileMapper.mapToCreateUserRequest(req))
        );
    }
}
