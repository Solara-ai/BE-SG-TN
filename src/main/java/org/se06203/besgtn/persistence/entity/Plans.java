package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "plans")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("plans")
public class Plans {

    @MongoId(FieldType.STRING)
    @Builder.Default
    private String id = new ObjectId().toString();
    private String epicId;
    private String date;
    private Integer quantityTask;
    private List<String> taskIds;
    private String status;
}


