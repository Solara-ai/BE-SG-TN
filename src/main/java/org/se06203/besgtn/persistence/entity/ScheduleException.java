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

@Document(collection = "schedule_exceptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("schedule_exceptions")
public class ScheduleException {
    @Id
    private String id;
    private String scheduleId;
    @Field(targetType = FieldType.STRING)
    private String exceptionDate; // yyyy-MM-dd
    private String reason;
    @Field(targetType = FieldType.DATE_TIME)
    private Instant createdAt = Instant.now();
}