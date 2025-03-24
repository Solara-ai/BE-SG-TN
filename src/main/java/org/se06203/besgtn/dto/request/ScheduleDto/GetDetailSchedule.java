package org.se06203.besgtn.dto.request.ScheduleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetDetailSchedule {
    private String scheduleId;
    private String eventId;
    private String eventName;
    private String description;
    private String startTime;
    private String endTime;
    private String date;
    private String endDate;
    private Boolean remindMe;
    private String repeat;
    private CategoriesItem categories;
}
