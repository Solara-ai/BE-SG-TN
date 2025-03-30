package org.se06203.besgtn.dto.response.scheduleDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GetDateTimeRes {
    @Schema(example = "yyy-mm-dd")
    private LocalDate date;
    private List<String> CategoryColor;
}
