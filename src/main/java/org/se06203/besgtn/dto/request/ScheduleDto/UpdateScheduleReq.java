package org.se06203.besgtn.dto.request.ScheduleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.se06203.besgtn.utils.Constants;

@Data
@Builder
@AllArgsConstructor
public class UpdateScheduleReq {
    private String name;
    private String description;
    private String date;
    private Constants.RepeatType repeat;
    private String repeatEndDate;
    private boolean remindMe;
    private String categoryId;
    private String startTime;
    private String endTime;
    private Constants.RepeatUpdateType updatedType;
    private String eventId;
}
