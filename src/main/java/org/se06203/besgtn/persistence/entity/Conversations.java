package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "conversations")
@Data
public class Conversations {
    @Id
    private String id;
    private String userId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private List<String> messageIds;
}
