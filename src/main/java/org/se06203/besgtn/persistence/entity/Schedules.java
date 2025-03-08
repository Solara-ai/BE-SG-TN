package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedules {
    @Id
    private String id;
    private String userId;
    private String name;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String repeat;
    private String repeatEndDate;
    private boolean remindMe;
    private String categoryId;
    private String parentScheduleId;
}