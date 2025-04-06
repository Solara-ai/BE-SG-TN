package org.se06203.besgtn.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.se06203.besgtn.dto.response.scheduleDto.CategoriesItem;
import org.se06203.besgtn.utils.Constants;

@Data
@Builder
@AllArgsConstructor
public class GenEvent {
    private String eventName;
    private String description;
    @Schema(example = "hh:MM:ss")
    private String startTime;
    @Schema(example = "hh:MM:ss")
    private String endTime;
    @Schema(example = "yyy-mm-dd")
    private String date;
    @Schema(example = "yyy-mm-dd")
    private String endDate;
    private Boolean remindMe;
    private Constants.RepeatType repeat;
    private CategoriesItem categories;
}
