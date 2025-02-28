package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "task_management")
@Data
public class TaskManagement {
    @Id
    private String id;
    private String nameEpic;
    private String type;
    private String description;
    private List<String> taskIds;
}

