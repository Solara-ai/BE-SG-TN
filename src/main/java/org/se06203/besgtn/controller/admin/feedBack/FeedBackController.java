package org.se06203.besgtn.controller.admin.feedBack;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.PagedData;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.dto.request.InsertFeedBackReq;
import org.se06203.besgtn.dto.response.FeedBackDetailRes;
import org.se06203.besgtn.dto.response.SearchFeedBackRes;
import org.se06203.besgtn.service.admin.impl.AdminFeedBackService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class FeedBackController implements FeedBackApi{
    private final ResponseFactory responseFactory;
    private final AdminFeedBackService adminFeedBackService;

    @Override
    public ResponseEntity<BaseResponse> sendFeedBack(InsertFeedBackReq request) {
        adminFeedBackService.sendFeedBack(request);
        return responseFactory.success(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BaseDataResponse<PagedData<SearchFeedBackRes>>> getFeedBack(Pageable pageable) {
        return responseFactory.success(HttpStatus.OK, adminFeedBackService.getFeedBack(pageable));
    }

    @Override
    public ResponseEntity<BaseDataResponse<FeedBackDetailRes>> getFeedBackDetail(String id) {
        return responseFactory.success(HttpStatus.OK, adminFeedBackService.getFeedBackDetail(id));
    }
}
