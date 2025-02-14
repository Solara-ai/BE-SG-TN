package org.se06203.sgtmbackend.config.exception.response;

import org.apache.commons.lang3.StringUtils;
import org.se06203.sgtmbackend.config.ErrorCode;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import java.util.Locale;

public class ResponseFactory {

    private final MessageSource messageSource;

    public ResponseFactory(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static ResponseFactory INSTANCE;

    private String getErrorCodeMessageKey(ErrorCode errorCode) {
        return getErrorCodeMessageKey(errorCode.name());
    }

    private String getErrorCodeMessageKey(String code) {
        return String.join(".", "error", code);
    }

    private String getMessage(ErrorCode code) {
        return this.getMessage(code, new String[0]);
    }

    private String getMessage(ErrorCode code, String... args) {
        return messageSource.getMessage(
                getErrorCodeMessageKey(code),
                args,
                getErrorCodeMessageKey(ErrorCode.FAILED),
                Locale.ENGLISH);
    }

    public <T> ResponseWrapper<T> success(T data) {
        return ResponseWrapper.<T>builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.SUCCESS.getCode())
                .message(this.getMessage(ErrorCode.SUCCESS))
                .data(data)
                .build();
    }

    public ResponseWrapper<Void> success() {
        return ResponseWrapper.<Void>builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.SUCCESS.getCode())
                .message(this.getMessage(ErrorCode.SUCCESS))
                .build();
    }

    public ResponseWrapper<Void> error() {
        return ResponseWrapper.<Void>builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(this.getMessage(ErrorCode.FAILED))
                .build();
    }

    public ResponseWrapper<Void> error(ErrorCode errorCode) {
        return ResponseWrapper.<Void>builder()
                .status(errorCode.getStatus().value())
                .code(errorCode.getCode())
                .message(this.getMessage(errorCode))
                .build();
    }

    public ResponseWrapper<Void> error(ErrorCode errorCode, String... args) {
        return ResponseWrapper.<Void>builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(errorCode.getCode())
                .message(this.getMessage(errorCode, args))
                .build();
    }

    public ResponseWrapper<Void> errorWithCustomMsg(String message) {
        return ResponseWrapper.<Void>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.INVALID_INPUT.getCode())
                .message(message)
                .build();
    }

    public ResponseWrapper<Void> errorWithCustomMsg(ErrorCode errorCode, String message) {
        return ResponseWrapper.<Void>builder()
                .status(errorCode.getStatus().value())
                .code(errorCode.getCode())
                .message(message)
                .build();
    }

    public ResponseWrapper<Void> errorWithCustomMsg(ErrorCode errorCode, String message, String... args) {
        if (args != null && args.length > 0) {
            return ResponseWrapper.<Void>builder()
                    .status(errorCode.getStatus().value())
                    .code(errorCode.getCode())
                    .message(StringUtils.isBlank(message) ? this.getMessage(errorCode, args) : message)
                    .build();
        }
        return ResponseWrapper.<Void>builder()
                .status(errorCode.getStatus().value())
                .code(errorCode.getCode())
                .message(StringUtils.isBlank(message) ? this.getMessage(errorCode) : message)
                .build();
    }
}