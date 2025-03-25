package org.se06203.besgtn.controller.admin.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.dto.request.EmailRequest;
import org.se06203.besgtn.dto.request.RegisterAdminRequest;
import org.se06203.besgtn.dto.response.AuthenticateResponse;
import org.se06203.besgtn.service.AuthService;
import org.se06203.besgtn.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenAdminController implements AuthenticationApi {
    private final AuthService authService;
    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<BaseDataResponse<AuthenticateResponse>> login(EmailRequest request) {
        return responseFactory.success(HttpStatus.OK,
                authService.authenticate(request, Constants.AuthorityEnum.ADMIN));
    }

    @Override
    public ResponseEntity<BaseResponse> register(RegisterAdminRequest request) {
        return responseFactory.success(HttpStatus.CREATED, authService.register(request));
    }
}
