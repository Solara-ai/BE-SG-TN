package org.se06203.besgtn.config.exception;

import org.se06203.besgtn.config.ErrorCode;

public class RegisterPhoneExistException extends ServerException {
    public RegisterPhoneExistException() {
        super(ErrorCode.PHONE_EXISTED);
    }
}
