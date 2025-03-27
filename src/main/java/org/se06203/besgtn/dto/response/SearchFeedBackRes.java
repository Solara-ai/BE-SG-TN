package org.se06203.besgtn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class SearchFeedBackRes {
    private String id;
    private String userId;
    private String message;
    private Instant updatedAt;
}
