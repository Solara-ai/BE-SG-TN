package org.se06203.besgtn.dto.request.ScheduleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class GetDateTime {
    private LocalDate date;
    private String startTime;
    private String endTime;
    private String CategoryColor;
}
