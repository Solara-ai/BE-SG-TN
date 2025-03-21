package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("tasks")
public class Tasks {
    @Id
    private String id;
    private String epicId;
    private String description;
}

