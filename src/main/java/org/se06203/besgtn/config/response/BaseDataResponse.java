package org.se06203.besgtn.config.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class BaseDataResponse<D> extends BaseResponse {

    private D data;

    @Builder(builderMethodName = "build")
    public BaseDataResponse(final Integer httpStatus,
                            final HttpStatus resultCode,
                            final String resultMsg,
                            final String resourceId,
                            final D data) {
        super(httpStatus, resultCode, resultMsg, resourceId);
        this.data = data;
    }
}
