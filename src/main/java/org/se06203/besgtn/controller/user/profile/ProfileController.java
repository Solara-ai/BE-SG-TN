package org.se06203.besgtn.controller.user.profile;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.dto.request.ProfileRequest;
import org.se06203.besgtn.dto.response.ProfileResponse;
import org.se06203.besgtn.service.users.impl.UsersProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class ProfileController implements ProfileApi{

    private final UsersProfileService usersProfileService;
    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<BaseDataResponse<ProfileResponse>> getProfile() {
        return responseFactory.success(HttpStatus.OK, usersProfileService.getProfile());
    }

    @Override
    public ResponseEntity<BaseResponse> updateProfile(ProfileRequest profileRequest) {
        usersProfileService.updateProfile(profileRequest);
        return responseFactory.success(HttpStatus.OK);
    }
}
