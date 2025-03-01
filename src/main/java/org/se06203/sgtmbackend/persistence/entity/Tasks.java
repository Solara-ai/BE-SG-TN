package org.se06203.sgtmbackend.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tasks")
public class Tasks {
    @Id

    private String id;

    private String epicId;

    private String description;
}
