package org.se06203.sgtmbackend.config.exception;


import org.se06203.sgtmbackend.config.ErrorCode;

public class RegisterEmailExistException extends ServerException {
    public RegisterEmailExistException() {
        super(ErrorCode.EMAIL_EXISTED);
    }
}
