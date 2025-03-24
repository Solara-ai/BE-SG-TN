package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("schedule_exceptions")
public class ScheduleException {
    @Id
    @Builder.Default
    private String id = new ObjectId().toString();
    @Field(targetType = FieldType.STRING)
    private String exceptionDate; // yyyy-MM-dd
    private String statTime; // HH:mm:ss
    private String endTime; // HH:mm:ss
    private String reason;
    @Field(targetType = FieldType.DATE_TIME)
    @Builder.Default
    private Instant createdAt = Instant.now();
}