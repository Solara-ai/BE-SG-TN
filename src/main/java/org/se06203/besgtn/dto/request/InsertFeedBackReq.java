package org.se06203.besgtn.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InsertFeedBackReq {
    private String feedBackId;
    @NotNull
    private String message;
}
