package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.se06203.besgtn.utils.Constants;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "tasks")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("tasks")
public class Tasks {

    @MongoId(FieldType.STRING)
    @Builder.Default
    private String id = new ObjectId().toString();
    private String title;
    private String epicId;
    private String description;
    @Enumerated(EnumType.STRING)
    @Field(targetType = FieldType.STRING)
    private Constants.TypeTask status;
}

