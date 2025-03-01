package org.se06203.sgtmbackend.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.se06203.sgtmbackend.config.exception.response.ResponseWrapper;
import org.se06203.sgtmbackend.dto.request.EmailRequest;
import org.se06203.sgtmbackend.dto.request.RegisterRequest;
import org.se06203.sgtmbackend.dto.response.AuthenticateResponse;
import org.se06203.sgtmbackend.service.AuthenticateService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users/authentication")
public class AuthenticationController {

    private final AuthenticateService authenticateService;

    @Operation(
            summary = "Authenticate user by email",
            description = "desc",
            tags = "User - Authentication"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failed"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    @PostMapping("/authenticate")
    public ResponseWrapper<AuthenticateResponse> authenticate(@Validated @RequestBody EmailRequest request) {
        return ResponseWrapper.success(authenticateService.authenticate(request));
    }

    @Operation(
            summary = "Register user by email",
            description = "desc",
            tags = "User - Authentication"
    )
    @PostMapping("/register")
    public ResponseWrapper<AuthenticateResponse> register(@Validated @RequestBody RegisterRequest request){
        return ResponseWrapper.success(authenticateService.register(request));
    }
}
