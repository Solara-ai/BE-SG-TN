package org.se06203.besgtn.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.se06203.besgtn.config.exception.handler.BaseErrorCodeMsg;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodeMsg implements BaseErrorCodeMsg {

    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, "access denied"),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, "unauthorized"),
    PASSWORD_OR_EMAIL_NOT_MATCH(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND , "Password or email not match" ),

    // validate
    MISSING_PARAMETERS(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "missing parameters"),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "invalid email"),
    INVALID_PHONE_NUMBER(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "invalid phone number"),

    // users
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST , "User already exist"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND , "User not found"),
    REPEAT_NOT_FOUND(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND , "Repeat not found" ),

    // schedules
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND , "Schedule not found" ),
    CATEGORY_NOT_FOUND(HttpStatus.NO_CONTENT.value(), HttpStatus.NOT_FOUND , "Category not found" ),
    INVALID_AUTHORITY(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND , "Invalid authority" ),
    FEEDBACK_NOT_FOUND(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND , "Feedback not found" ),;

    private final int httpStatus;
    private final HttpStatus resultCode;
    private final String resultMsg;
}
