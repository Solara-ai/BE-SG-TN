package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedbacks")
@Data
public class FeedBacks {
    @Id
    private String id;
    private String userId;
    private String adminId;
    private String message;
}


