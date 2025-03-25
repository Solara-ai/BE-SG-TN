package org.se06203.besgtn.service.admin.impl;

import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.config.security.SecurityUtils;
import org.se06203.besgtn.dto.request.InsertFeedBackReq;
import org.se06203.besgtn.persistence.entity.FeedBacks;
import org.se06203.besgtn.persistence.entity.Messages;
import org.se06203.besgtn.persistence.repository.FeedBackRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminFeedBackService {

    private final FeedBackRepository feedBackRepository;

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
}
