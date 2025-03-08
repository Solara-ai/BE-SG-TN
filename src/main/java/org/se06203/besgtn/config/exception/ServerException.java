package org.se06203.besgtn.config.exception;

import lombok.Getter;
import org.se06203.besgtn.config.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public class ServerException extends RuntimeException {
    private final ErrorCode errorCode;
    private final HttpStatus status;
    private final String errorMessage;
    private String[] args;

    public ServerException() {
        super(new Throwable());
        this.errorCode = ErrorCode.FAILED;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errorMessage = "";
    }

    public ServerException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.status = errorCode.getStatus();
        this.errorMessage = "";
    }

    public ServerException(String errorMessage) {
        super(new Throwable());
        this.errorCode = ErrorCode.FAILED;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errorMessage = errorMessage;
    }

    public ServerException(ErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.status = errorCode.getStatus();
        this.errorMessage = errorMessage;
    }

    public ServerException(ErrorCode errorCode, String errorMessage, String... args) {
        this(errorCode, errorMessage);
        this.args = args;
    }
}
