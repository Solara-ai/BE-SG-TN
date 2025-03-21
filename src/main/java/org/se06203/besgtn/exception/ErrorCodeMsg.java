package org.se06203.besgtn.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.se06203.besgtn.config.exception.handler.BaseErrorCodeMsg;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodeMsg implements BaseErrorCodeMsg {

    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), HttpStatus.UNAUTHORIZED, "access denied"),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST , "User already exist"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND , "User not found"),
    PASSWORD_OR_EMAIL_NOT_MATCH(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND , "Password or email not match" ),
    REPEAT_NOT_FOUND(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND , "Repeat not found" ),
    ;

    private final int httpStatus;
    private final HttpStatus resultCode;
    private final String resultMsg;
}
