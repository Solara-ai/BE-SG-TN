package org.se06203.besgtn.controller.admin.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.dto.admin.response.GetListUsersResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Users", description = "Api for admin")
public interface UserManageApi {

    @GetMapping("/all")
    ResponseEntity<BaseDataResponse<List<GetListUsersResponse>>> getAllUsers();

}
