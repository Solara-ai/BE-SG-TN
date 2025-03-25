package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.List;

@Document(collection = "organizations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("organizations")
public class Organizations {

    @MongoId(FieldType.STRING)
    @Builder.Default
    private String id = new ObjectId().toString();
    private String name;
    private List<String> admins;
    private List<String> members;
    @Field(targetType = FieldType.DATE_TIME)
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Field(targetType = FieldType.DATE_TIME)
    private Instant updatedAt;
    @Field(targetType = FieldType.DATE_TIME)
    private Instant deletedAt;

}
