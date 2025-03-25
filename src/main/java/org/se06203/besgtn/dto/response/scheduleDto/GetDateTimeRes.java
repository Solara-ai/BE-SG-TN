package org.se06203.besgtn.dto.response.scheduleDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class GetDateTimeRes {
    @Schema(example = "yyy-mm-dd")
    private LocalDate date;
    @Schema(example = "hh:MM:ss")
    private String startTime;
    @Schema(example = "hh:MM:ss")
    private String endTime;
    private String CategoryColor;
}
