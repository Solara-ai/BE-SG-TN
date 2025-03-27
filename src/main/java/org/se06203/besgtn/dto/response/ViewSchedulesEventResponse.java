package org.se06203.besgtn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ViewSchedulesEventResponse {
    private String id;
    private String title;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
}
