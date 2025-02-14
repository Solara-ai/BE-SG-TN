package org.se06203.sgtmbackend.config.exception.handler;


import lombok.extern.slf4j.Slf4j;
import org.se06203.sgtmbackend.config.ErrorCode;
import org.se06203.sgtmbackend.config.exception.*;
import org.se06203.sgtmbackend.config.exception.response.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
@Slf4j
@ResponseBody
public class MyExceptionHandler {
    private static final String METHOD_ARGUMENT_INVALID_TEMPLATE_MSG = "Field '%s': %s";

    @ExceptionHandler(value = SocialUserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> exception(SocialUserNotFoundException exception) {
        log.error("SocialUserNotFoundException", exception);
        return ResponseWrapper
                .errorWithCustomMsg(exception.getErrorCode(), exception.getErrorMessage());
    }

    @ExceptionHandler(value = ServerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> exception(ServerException exception) {
        log.error("ServerException", exception);
        return ResponseWrapper.errorWithCustomMsg(exception.getErrorCode(), exception.getErrorMessage(), exception.getMessage());
    }

    @ExceptionHandler(value = IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> exception(IncorrectPasswordException exception) {
        log.error("IncorrectPasswordException", exception);
        return ResponseWrapper.errorWithCustomMsg(exception.getErrorCode(), exception.getErrorMessage(), exception.getMessage());
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> exception(NotFoundException exception) {
        log.error("NotFoundException", exception);
        return ResponseWrapper.errorWithCustomMsg(exception.getErrorCode(), exception.getErrorMessage(), exception.getMessage());
    }

    @ExceptionHandler(value = NotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> exception(NotSupportedException exception) {
        log.error("NotSupportedException", exception);
        return ResponseWrapper.errorWithCustomMsg(exception.getErrorCode(), exception.getErrorMessage(), exception.getMessage());
    }

    @ExceptionHandler(value = OtpInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> exception(OtpInvalidException exception) {
        log.error("OtpInvalidException", exception);
        return ResponseWrapper.errorWithCustomMsg(exception.getErrorCode(), exception.getErrorMessage(), exception.getMessage());
    }

    @ExceptionHandler(value = RegisterEmailExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> exception(RegisterEmailExistException exception) {
        log.error("RegisterEmailExistException", exception);
        return ResponseWrapper.errorWithCustomMsg(exception.getErrorCode(), exception.getErrorMessage(), exception.getMessage());
    }

    @ExceptionHandler(value = RegisterPhoneExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> exception(RegisterPhoneExistException exception) {
        log.error("RegisterPhoneExistException", exception);
        return ResponseWrapper.errorWithCustomMsg(exception.getErrorCode(), exception.getErrorMessage(), exception.getMessage());
    }

    @ExceptionHandler(value = InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> handleException(InvalidInputException exception) {
        log.error("InvalidInputException", exception);
        return ResponseWrapper.errorWithCustomMsg(ErrorCode.INVALID_INPUT, exception.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> exception(AccessDeniedException exception) {
        log.error("AccessDeniedException", exception);
        return ResponseWrapper.error(ErrorCode.ACCESS_DENIED);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> exception(BadCredentialsException exception) {
        log.error("BadCredentialsException", exception);
        return ResponseWrapper.error(ErrorCode.ACCESS_DENIED);
    }

    @ExceptionHandler(value = MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> handleMissingPathVariable(MissingPathVariableException ex) {
        log.error("MissingPathVariableException", ex);
        return ResponseWrapper.errorWithCustomMsg(ErrorCode.INVALID, ex.getMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        log.error("MissingServletRequestParameterException", ex);
        return ResponseWrapper.errorWithCustomMsg(ErrorCode.INVALID, ex.getMessage());
    }

    @ExceptionHandler(value = MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> handleMissingServletRequestPart(MissingServletRequestPartException ex) {
        log.error("MissingServletRequestPartException", ex);
        return ResponseWrapper.errorWithCustomMsg(ErrorCode.INVALID, ex.getMessage());
    }

    @ExceptionHandler(value = ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> handleServletRequestBindingException(ServletRequestBindingException ex) {
        log.error("ServletRequestBindingException", ex);
        return ResponseWrapper.errorWithCustomMsg(ErrorCode.INVALID, ex.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException", ex);
        var sb = ex.getBindingResult().getFieldErrors().stream()
                .reduce(new StringBuilder(),
                        (current, next) -> current.append(METHOD_ARGUMENT_INVALID_TEMPLATE_MSG.formatted(next.getField(), next.getDefaultMessage())).append("\n"),
                        StringBuilder::append);
        var msg = sb.replace(sb.length() - 1, sb.length(), "").toString();

        return ResponseWrapper.errorWithCustomMsg(ErrorCode.MISSING_PARAMETERS, msg);
    }

    @ExceptionHandler(value = HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        log.error("HandlerMethodValidationException", ex);
        return ResponseWrapper.errorWithCustomMsg(ErrorCode.INVALID_INPUT, ex.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Void> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException", ex);
        return ResponseWrapper.errorWithCustomMsg(ErrorCode.INVALID_INPUT, ex.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapper<Void> handleException(Exception exception) {
        log.error("Exception", exception);
        return ResponseWrapper.errorWithCustomMsg(ErrorCode.INVALID, exception.getMessage());
    }
}
