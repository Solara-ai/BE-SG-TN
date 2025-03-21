package org.se06203.besgtn.dto.request.ScheduleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.se06203.besgtn.utils.Constants;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InsertScheduleReq {
    private String name;
    private String description;
    private String date;
    private Constants.RepeatType repeat;
    private String repeatEndDate;
    private boolean remindMe;
    private String categoryId;
    private String startTime;
    private String endTime;
}
