package org.se06203.besgtn.dto.request;

import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateEventRequest {

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

}
