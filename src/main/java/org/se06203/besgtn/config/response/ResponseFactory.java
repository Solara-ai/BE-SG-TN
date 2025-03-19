package org.se06203.besgtn.config.response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.ErrorCode;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseFactory {

    public ResponseEntity<BaseResponse> success(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(
                BaseResponse.builder()
                        .httpStatus(httpStatus.value())
                        .resultCode(httpStatus)
                        .resultMsg("success")
                        .build());
    }

    public ResponseEntity<BaseResponse> success(HttpStatus httpStatus,
                                                String resourceId) {

        return ResponseEntity.status(httpStatus).body(
                BaseResponse.builder()
                        .httpStatus(httpStatus.value())
                        .resultCode(httpStatus)
                        .resultMsg("success")
                        .resourceId(resourceId)
                        .build());
    }

    public <E extends BaseRuntimeException> ResponseEntity<BaseResponse> fail(E exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(BaseResponse.builder()
                        .httpStatus(exception.getHttpStatus())
                        .resultCode(exception.getResultCode())
                        .resultMsg(exception.getResultMsg())
                        .build());
    }

    public ResponseEntity<BaseResponse> fail(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.builder()
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .resultCode(HttpStatus.INTERNAL_SERVER_ERROR)
                        .resultMsg(exception.getMessage())
                        .build());
    }

    public <E extends BaseRuntimeException, T> ResponseEntity<BaseDataResponse<T>> fail(E exception, T data) {

        return ResponseEntity.status(exception.getHttpStatus())
                .body(BaseDataResponse.<T>build()
                        .httpStatus(exception.getHttpStatus())
                        .resultCode(exception.getResultCode())
                        .resultMsg(exception.getResultMsg())
                        .data(data)
                        .build());
    }

    public <T> ResponseEntity<BaseDataResponse<T>> success(HttpStatus httpStatus, T data) {
        return ResponseEntity.status(httpStatus)
                .body(BaseDataResponse.<T>build()
                        .httpStatus(httpStatus.value())
                        .resultCode(httpStatus)
                        .resultMsg("success")
                        .data(data)
                        .build());
    }

    public <T> ResponseEntity<BaseDataResponse<T>> success(HttpStatus httpStatus, T data, String resourceId) {
        return ResponseEntity.status(httpStatus)
                .body(BaseDataResponse.<T>build()
                        .httpStatus(httpStatus.value())
                        .resultCode(httpStatus)
                        .resultMsg("success")
                        .resourceId(resourceId)
                        .data(data)
                        .build());
    }

    public <E extends BaseRuntimeException> ResponseEntity<BaseResponse> fail(ErrorCode errorCode) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(BaseResponse.builder()
                        .httpStatus(HttpStatus.FORBIDDEN.value())
                        .resultCode(errorCode.getStatus())
                        .resultMsg(errorCode.getCode())
                        .build());
    }

    public <E extends BaseRuntimeException> ResponseEntity<BaseResponse> fail(ErrorCode errorCode, String role, String feature) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(BaseResponse.builder()
                        .httpStatus(errorCode.getStatus().value())
                        .resultCode(errorCode.getStatus())
                        .resultMsg(role + " does not have access to " + feature)
                        .build());
    }
}
