package org.se06203.besgtn.dto.response.scheduleDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetListScheduleRes {
    private String scheduleId;
    private String eventId;
    private String eventName;
    private String description;
    @Schema(example = "hh:MM:ss")
    private String startTime;
    @Schema(example = "hh:MM:ss")
    private String endTime;
    @Schema(example = "yyy-mm-dd")
    private String date;
    private String categoryColor;
}
