package org.se06203.besgtn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.se06203.besgtn.persistence.entity.Messages;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class FeedBackDetailRes {
    private String id;
    private String userId;
    private List<Messages> messages;
    private Instant createdAt;
}
