package org.se06203.besgtn.config.exception;

import org.se06203.besgtn.config.ErrorCode;

public class RegisterEmailExistException extends ServerException {
    public RegisterEmailExistException() {
        super(ErrorCode.EMAIL_EXISTED);
    }
}
