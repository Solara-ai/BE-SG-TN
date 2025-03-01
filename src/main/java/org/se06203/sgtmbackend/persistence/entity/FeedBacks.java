package org.se06203.sgtmbackend.persistence.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "feedbacks")
public class FeedBacks {
    @Id

    private String id;


    private String usersId;


    private String adminId;

    private String messages;
}
