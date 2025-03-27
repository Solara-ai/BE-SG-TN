package org.se06203.besgtn.service.admin.impl;

import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.config.response.PagedData;
import org.se06203.besgtn.config.security.SecurityUtils;
import org.se06203.besgtn.dto.request.InsertFeedBackReq;
import org.se06203.besgtn.dto.response.FeedBackDetailRes;
import org.se06203.besgtn.dto.response.SearchFeedBackRes;
import org.se06203.besgtn.persistence.entity.FeedBacks;
import org.se06203.besgtn.persistence.entity.Messages;
import org.se06203.besgtn.persistence.repository.FeedBackRepository;
import org.se06203.besgtn.utils.mapper.FeedBacksMapper;
import org.se06203.besgtn.utils.mapper.PagedDataMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminFeedBackService {

    private final FeedBackRepository feedBackRepository;
    private final PagedDataMapper pagedDataMapper;
    private final FeedBacksMapper feedBacksMapper;

    @Transactional
    public void sendFeedBack(InsertFeedBackReq request) {
        var user = SecurityUtils.getAuthenticatedUser();

        var message = Messages.builder()
                .message(request.getMessage())
                .userId(user.getId())
                .role(user.getRole())
                .build();

        FeedBacks feedBack;
        if (request.getFeedBackId() == null) {
            feedBack = FeedBacks.builder()
                    .userId(user.getId())
                    .messages(List.of(message))
                    .createdAt(Instant.now())
                    .build();
        } else {
            feedBack = feedBackRepository.findById(request.getFeedBackId())
                    .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.FEEDBACK_NOT_FOUND));
            feedBack.getMessages().add(message);
            feedBack.setUpdatedAt(Instant.now());
        }

        feedBackRepository.save(feedBack);
    }

    public PagedData<SearchFeedBackRes> getFeedBack(Pageable pageable) {
        var feedBacks = feedBackRepository.findAll(pageable);

        return pagedDataMapper.mapToPagedData(feedBacks.map(feedBack -> {
            var latestMessage = feedBack.getMessages().stream()
                    .max(Comparator.comparing(Messages::getCreatedAt))
                    .map(Messages::getMessage)
                    .orElse(null);

            return new SearchFeedBackRes(
                    feedBack.getId(),
                    feedBack.getUserId(),
                    latestMessage,
                    feedBack.getUpdatedAt()
            );
        }));
    }

    public FeedBackDetailRes getFeedBackDetail(String id) {
        var feedBack = feedBackRepository.findById(id)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.FEEDBACK_NOT_FOUND));

        return feedBacksMapper.mapToFeedBackDetailRes(feedBack);
    }
}
