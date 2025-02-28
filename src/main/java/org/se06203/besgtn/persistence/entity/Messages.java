package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "messages")
@Data
public class Messages {
    @Id
    private String id;
    private String conversationId;
    private org.bson.Document message;
    private LocalDateTime createdAt;
}


