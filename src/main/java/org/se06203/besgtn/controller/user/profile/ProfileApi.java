package org.se06203.besgtn.controller.user.profile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.dto.request.ProfileRequest;
import org.se06203.besgtn.dto.response.ProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/profile")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Profile", description = "Api for user profile")
public interface ProfileApi {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Profile", summary = "Get user profile.")
    ResponseEntity<BaseDataResponse<ProfileResponse>> getProfile();

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Profile", summary = "Update user profile.")
    ResponseEntity<BaseResponse> updateProfile(@RequestBody ProfileRequest profileRequest);
}
