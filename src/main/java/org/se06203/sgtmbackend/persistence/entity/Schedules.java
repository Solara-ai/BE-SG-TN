package org.se06203.sgtmbackend.persistence.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "schedules")
public class Schedules {
    @Id

    private String id;

    private String userId;

    private String name;

    private String description;

    private String startTime;
    private String endTime;
    private String date;
    private boolean isException;
    private String repeat;
    private String repeatEndDate;
    private boolean remindMe;

}
