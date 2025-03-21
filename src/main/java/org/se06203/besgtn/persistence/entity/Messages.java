package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.se06203.besgtn.utils.Constants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Messages {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @Field(targetType = FieldType.STRING)
    private Constants.AuthorityEnum role;

    private String message;

    @Field(targetType = FieldType.DATE_TIME)
    private Instant createdAt = Instant.now();
}


