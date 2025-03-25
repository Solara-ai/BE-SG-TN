package org.se06203.besgtn.controller.user.feedBack;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.dto.request.InsertFeedBackReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/feedback")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Feedback Management", description = "Feedback Management Api")
public interface FeedBackApi {

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "Feedback Management", summary = "Send feedback")
    ResponseEntity<BaseResponse> sendFeedBack(@RequestBody InsertFeedBackReq request);
}
