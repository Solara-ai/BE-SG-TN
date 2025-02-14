package org.se06203.sgtmbackend.config.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.se06203.sgtmbackend.config.ErrorCode;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> {

    private String code;
    private int status;
    private String message;
    private T data;

    public static ResponseWrapper<Void> success() {
        return ResponseFactory.INSTANCE.success();
    }

    public static <T> ResponseWrapper<T> success(T data) {
        return ResponseFactory.INSTANCE.success(data);
    }

    public static ResponseWrapper<Void> error() {
        return ResponseFactory.INSTANCE.error();
    }

    public static ResponseWrapper<Void> error(ErrorCode errorCode) {
        return ResponseFactory.INSTANCE.error(errorCode);
    }

    public static ResponseWrapper<Void> error(ErrorCode errorCode, String... args) {
        return ResponseFactory.INSTANCE.error(errorCode, args);
    }

    public static ResponseWrapper<Void> errorWithCustomMsg(String msg) {
        return ResponseFactory.INSTANCE.errorWithCustomMsg(msg);
    }

    public static ResponseWrapper<Void> errorWithCustomMsg(ErrorCode errorCode, String msg) {
        return ResponseFactory.INSTANCE.errorWithCustomMsg(errorCode, msg);
    }

    public static ResponseWrapper<Void> errorWithCustomMsg(ErrorCode errorCode, String msg, String... args) {
        return ResponseFactory.INSTANCE.errorWithCustomMsg(errorCode, msg, args);
    }
}