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

@Document(collection = "conversations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("conversations")
public class Conversations {

    @MongoId(FieldType.STRING)
    @Builder.Default
    private String id = new ObjectId().toString();
    private String userId;

    @Field(targetType = FieldType.DATE_TIME)
    private Instant startedAt = Instant.now();

    private List<Messages> messages;
}
