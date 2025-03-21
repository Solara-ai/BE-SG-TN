package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "conversations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("conversations")
public class Conversations {
    @Id
    private String id;
    private String userId;

    @Field(targetType = FieldType.DATE_TIME)
    private Instant startedAt = Instant.now();

    @Field(targetType = FieldType.DATE_TIME)
    private Instant endedAt;

    private List<Messages> messages;
}
