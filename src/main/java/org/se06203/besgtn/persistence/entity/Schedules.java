package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.se06203.besgtn.utils.Constants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private Constants.RepeatType repeat;
    private String repeatEndDate;
    private boolean remindMe;
    private String categoryId;
    private String parentScheduleId;
    private List<ChildSchedule> childSchedules;
}