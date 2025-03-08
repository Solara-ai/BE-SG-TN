package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "schedule_exceptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleException {
    @Id
    private String id;
    private String scheduleId;
    private LocalDate exceptionDate;
    private String reason;
    private LocalDateTime createdAt;
}