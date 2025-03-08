package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedbacks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedBacks {
    @Id
    private String id;
    private String userId;
    private String adminId;
    private String message;
}


