package org.se06203.besgtn.dto.request.scheduleDto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "yyy-mm-dd")
    private String date;
    @Schema(example = "<DAILY, WEEKLY, MONTHLY, YEARLY>")
    private Constants.RepeatType repeat;
    @Schema(example = "yyy-mm-dd")
    private String repeatEndDate;
    private boolean remindMe;
    private String categoryId;
    @Schema(example = "hh:MM:ss")
    private String startTime;
    @Schema(example = "hh:MM:ss")
    private String endTime;
    @Schema(example = "<ALL, THIS, CONTINUE>")
    private Constants.RepeatUpdateType updatedType;
    private String eventId;
}
