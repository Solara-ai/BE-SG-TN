package org.se06203.sgtmbackend.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "schedules_exceptions")
public class SchedulesExceptions {
    @Id

    private String id;



    private String scheduleId;

    private String exceptionDate;
    private String reason;
    private String createdAt;
}
