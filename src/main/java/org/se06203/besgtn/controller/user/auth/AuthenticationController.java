package org.se06203.besgtn.controller.user.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.dto.request.EmailRequest;
import org.se06203.besgtn.dto.request.RegisterRequest;
import org.se06203.besgtn.dto.response.AuthenticateResponse;
import org.se06203.besgtn.service.users.impl.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final ResponseFactory responseFactory;
    private final AuthService authService;

    @Override
    public ResponseEntity<BaseDataResponse<AuthenticateResponse>> login(EmailRequest request) {
        return responseFactory.success(HttpStatus.OK, authService.authenticate(request));
    }

    @Override
    public ResponseEntity<BaseResponse> register(RegisterRequest request) {
        return responseFactory.success(HttpStatus.CREATED, authService.register(request));
    }
}
