package org.se06203.besgtn.service.users.impl;

import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.config.security.SecurityUtils;
import org.se06203.besgtn.dto.request.InsertFeedBackReq;
import org.se06203.besgtn.persistence.entity.FeedBacks;
import org.se06203.besgtn.persistence.entity.Messages;
import org.se06203.besgtn.persistence.repository.FeedBackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsesFeedBackService {

    private final FeedBackRepository feedBackRepository;

    @Transactional
    public void sendFeedBack(InsertFeedBackReq request) {
        var user = SecurityUtils.getAuthenticatedUser();

        var message = Messages.builder()
                .message(request.getMessage())
                .userId(user.getId())
                .role(user.getRole())
                .build();

        var feedBack = FeedBacks.builder()
                .userId(user.getId())
                .messages(List.of(message))
                .build();

        feedBackRepository.save(feedBack);
    }
}
