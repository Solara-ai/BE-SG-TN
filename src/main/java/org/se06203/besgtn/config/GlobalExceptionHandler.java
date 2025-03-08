package org.se06203.besgtn.config;

import groovy.util.logging.Slf4j;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@Hidden
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ResponseFactory responseFactory;
    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseEntity<BaseResponse> handleBaseRuntimeException(BaseRuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseFactory.fail(ex).getBody());
    }
}
