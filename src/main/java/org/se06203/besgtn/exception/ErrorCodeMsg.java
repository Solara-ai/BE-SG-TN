package org.se06203.besgtn.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.se06203.besgtn.config.exception.handler.BaseErrorCodeMsg;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodeMsg implements BaseErrorCodeMsg {

    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST,3001 , "User already exist"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,3002 , "User not found"),
    PASSWORD_OR_EMAIL_NOT_MATCH(HttpStatus.NOT_FOUND,3003 , "Password or email not match" ),;

    private final HttpStatus httpStatus;
    private final int resultCode;
    private final String resultMsg;
}
