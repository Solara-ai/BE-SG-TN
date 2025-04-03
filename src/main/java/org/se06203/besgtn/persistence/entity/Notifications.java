package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.bson.types.ObjectId;
import org.se06203.besgtn.utils.Constants;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.List;


@Document(collection = "notification")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@TypeAlias("notification")
public class Notifications {

    @MongoId(FieldType.STRING)
    @Builder.Default
    private String id = new ObjectId().toString();
    private String token;  // Firebase token của user
    private String title;
    private String message;
    private String notificationType;
    @Enumerated(EnumType.STRING)
    private Constants.StatusNotification status;
    private Instant createdAt = Instant.now();
    private Instant sendTime = Instant.now();

}
