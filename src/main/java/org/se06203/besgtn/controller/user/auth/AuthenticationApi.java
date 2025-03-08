package org.se06203.besgtn.controller.user.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.dto.request.EmailRequest;
import org.se06203.besgtn.dto.request.RegisterRequest;
import org.se06203.besgtn.dto.response.AuthenticateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/auth")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Authentication", description = "Api for user authentication")
public interface AuthenticationApi {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "users - Account", summary = "User Authentication/User's Login.")
    ResponseEntity<BaseDataResponse<AuthenticateResponse>> login(@RequestBody @Valid EmailRequest request);

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "users - Account", summary = "User Registration.")
    ResponseEntity<BaseResponse> register(@RequestBody @Valid RegisterRequest request);
}
