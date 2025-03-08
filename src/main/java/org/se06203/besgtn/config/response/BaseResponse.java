package org.se06203.besgtn.config.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {

    private HttpStatus httpStatus;
    private Integer resultCode;
    private String resultMsg;
    private String resourceId;
    @Builder.Default
    private String responseTimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now());

    public BaseResponse(HttpStatus httpStatus,
                        Integer resultCode,
                        String resultMsg,
                        String resourceId) {
        this.httpStatus = httpStatus;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.resourceId = resourceId;
        this.responseTimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now());
    }
}