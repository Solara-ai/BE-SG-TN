package org.se06203.besgtn.controller.admin.feedBack;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.PagedData;
import org.se06203.besgtn.dto.request.InsertFeedBackReq;
import org.se06203.besgtn.dto.response.FeedBackDetailRes;
import org.se06203.besgtn.dto.response.SearchFeedBackRes;
import org.se06203.besgtn.persistence.entity.FeedBacks;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/feedback")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Feedback Management", description = "Feedback Management Api")
public interface FeedBackApi {

    @PostMapping("/reply")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "Feedback Management", summary = "Send feedback")
    ResponseEntity<BaseResponse> sendFeedBack(@RequestBody InsertFeedBackReq request);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Feedback Management", summary = "Get feedback")
    ResponseEntity<BaseDataResponse<PagedData<SearchFeedBackRes>>> getFeedBack(
            @PageableDefault(page = 0,
                    size = 25,
                    sort = FeedBacks.Fields.updatedAt) Pageable pageable);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Feedback Management", summary = "Get feedback Detail")
    ResponseEntity<BaseDataResponse<FeedBackDetailRes>> getFeedBackDetail(@PathVariable("id") String id);
}
