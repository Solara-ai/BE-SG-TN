package org.se06203.besgtn.controller.admin.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.dto.admin.response.GetListUsersResponse;
import org.se06203.besgtn.service.admin.impl.AdminViewListUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserManageController implements UserManageApi {
    private final ResponseFactory responseFactory;
    private final AdminViewListUserService adminViewListUserService;

    @Override
    public ResponseEntity<BaseDataResponse<List<GetListUsersResponse>>> getAllUsers() {
        return responseFactory.success(HttpStatus.OK,adminViewListUserService.getListUser());
    }
}
