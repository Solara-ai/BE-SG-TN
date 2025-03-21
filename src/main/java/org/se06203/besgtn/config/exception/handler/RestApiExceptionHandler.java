package org.se06203.besgtn.config.exception.handler;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.exception.RelatedResource;
import org.se06203.besgtn.config.exception.RelatedResourceException;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.PagedData;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestApiExceptionHandler {

    private final ResponseFactory responseFactory;

    @ExceptionHandler(RelatedResourceException.class)
    public ResponseEntity<BaseDataResponse<PagedData<RelatedResource>>> handleDeleteChainException(RelatedResourceException exception) {
        log.error("handleDeleteChainException().", exception);
        var pagedData = PagedData.<RelatedResource>builder()
                .elementList(exception.getRelatedResources())
                .build();
        return responseFactory.fail(exception, pagedData);
    }

    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseEntity<BaseResponse> handlerBaseRunTimeException(BaseRuntimeException baseRuntimeException) {
        log.error("handlerBaseRunTimeException().", baseRuntimeException);
        return responseFactory.fail(baseRuntimeException);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handlerException(Exception exception) {
        log.error("handlerException().", exception);
        return responseFactory.fail(exception);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<BaseResponse> handleNoResourceFoundException(NoResourceFoundException exception) {
        log.error("handleNoResourceFoundException().", exception);
        return responseFactory.fail(exception);
    }


    @ExceptionHandler(ServletException.class)
    public ResponseEntity<BaseResponse> handleServletException(ServletException exception) {
        log.error("handleServletException().", exception);
        return responseFactory.fail(exception);
    }

}
