package org.se06203.sgtmbackend.config.exception;

import org.se06203.sgtmbackend.config.ErrorCode;

public class RegisterPhoneExistException extends ServerException {
    public RegisterPhoneExistException() {
        super(ErrorCode.PHONE_EXISTED);
    }
}
