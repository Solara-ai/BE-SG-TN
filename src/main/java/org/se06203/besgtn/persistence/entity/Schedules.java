package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "schedules")
@Data
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