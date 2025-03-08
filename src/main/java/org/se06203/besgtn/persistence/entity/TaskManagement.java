package org.se06203.besgtn.persistence.entity;

import lombok.Data;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "task_management")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskManagement {
    @Id
    private String id;
    private String nameEpic;
    private String type;
    private String description;
    private List<String> taskIds;
}

