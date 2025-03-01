package org.se06203.sgtmbackend.persistence.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tasks_management")
public class TasksManagement {
    @Id
    private String id;

    private String nameEpic;

    private String type;

    private String description;

    private List<String> taskIds;
}
