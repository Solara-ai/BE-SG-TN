package org.se06203.besgtn.controller.admin.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.dto.admin.response.GetListUsersResponse;
import org.se06203.besgtn.dto.request.CreateUserRequest;
import org.se06203.besgtn.dto.response.ProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Users", description = "Api for admin")
public interface UserManageApi {

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Users", summary = "Get All Users.")
    ResponseEntity<BaseDataResponse<List<GetListUsersResponse>>> getAllUsers();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "Users", summary = "Create User.")
    ResponseEntity<BaseResponse> createUser(@RequestBody CreateUserRequest req);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Users", summary = "Get Profile.")
    ResponseEntity<BaseDataResponse<ProfileResponse>> getUserById();

}
