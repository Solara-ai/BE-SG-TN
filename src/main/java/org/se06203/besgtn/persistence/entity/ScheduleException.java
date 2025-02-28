package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "schedule_exceptions")
@Data
public class ScheduleException {
    @Id
    private String id;
    private String scheduleId;
    private LocalDate exceptionDate;
    private String reason;
    private LocalDateTime createdAt;
}