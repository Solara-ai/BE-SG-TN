package org.se06203.sgtmbackend.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "messages")
public class Messages {

    @Id
    private String id;

    private String conversationId;

    private String message;

    private String createdAt;

}
