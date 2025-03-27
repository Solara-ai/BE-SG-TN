package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.se06203.besgtn.utils.Constants;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("task_management")
@Builder(toBuilder = true)
public class TaskManagement {

    @MongoId(FieldType.STRING)
    @Builder.Default
    private String id = new ObjectId().toString();
    private String userId;
    private String nameEpic;
    @Enumerated(EnumType.STRING)
    @Field(targetType = FieldType.STRING)
    private Constants.TypeEpic type;
    private String description;
    private List<String> taskIds;
    @Field(targetType = FieldType.DATE_TIME)
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Field(targetType = FieldType.DATE_TIME)
    private Instant updatedAt;
    @Field(targetType = FieldType.DATE_TIME)
    private Instant deletedAt;
}

