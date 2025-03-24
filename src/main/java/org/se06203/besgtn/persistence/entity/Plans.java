package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "plans")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("plans")
public class Plans {
    @Id
    @Builder.Default
    private String id = new ObjectId().toString();
    private String epicId;
    private String date;
    private Integer quantityTask;
    private List<String> taskIds;
}


