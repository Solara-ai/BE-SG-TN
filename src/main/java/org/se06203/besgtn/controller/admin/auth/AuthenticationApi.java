package org.se06203.besgtn.controller.admin.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.dto.request.EmailRequest;
import org.se06203.besgtn.dto.request.RegisterAdminRequest;
import org.se06203.besgtn.dto.response.AuthenticateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/auth")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Authentication", description = "Api for admin authentication")
public interface AuthenticationApi {
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Authentication", summary = "Admin Authentication/Admin's Login.")
    ResponseEntity<BaseDataResponse<AuthenticateResponse>> login(@RequestBody @Valid EmailRequest request);

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "Authentication", summary = "Admin Registration.")
    ResponseEntity<BaseResponse> register(@RequestBody @Valid RegisterAdminRequest request);
}
