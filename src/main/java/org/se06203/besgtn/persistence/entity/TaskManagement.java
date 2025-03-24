package org.se06203.besgtn.persistence.entity;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "task_management")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("task_management")
public class TaskManagement {
    @Id
    @Builder.Default
    private String id = new ObjectId().toString();
    private String nameEpic;
    private String type;
    private String description;
    private List<String> taskIds;
}

