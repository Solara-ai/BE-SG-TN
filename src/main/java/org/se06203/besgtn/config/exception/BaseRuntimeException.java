package org.se06203.besgtn.config.exception;

import lombok.Getter;
import org.se06203.besgtn.config.exception.handler.BaseErrorCodeMsg;
import org.springframework.http.HttpStatus;

@Getter
public class BaseRuntimeException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final Integer resultCode;
    private final String resultMsg;

    public BaseRuntimeException(final BaseErrorCodeMsg baseErrorCodeMsg) {
        super(baseErrorCodeMsg.getResultMsg());
        this.httpStatus = baseErrorCodeMsg.getHttpStatus();
        this.resultCode = baseErrorCodeMsg.getResultCode();
        this.resultMsg = getMessage();
    }

}
