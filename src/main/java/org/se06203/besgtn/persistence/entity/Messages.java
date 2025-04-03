package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.se06203.besgtn.utils.Constants;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Messages {

    @MongoId(FieldType.STRING)
    @Builder.Default
    private String id = new ObjectId().toString();
    private String userId;
    @Enumerated(EnumType.STRING)
    @Field(targetType = FieldType.STRING)
    private Constants.AuthorityEnum role;

    private String message;

    @Field(targetType = FieldType.DATE_TIME)
    @Builder.Default
    private Instant createdAt = Instant.now();
}


