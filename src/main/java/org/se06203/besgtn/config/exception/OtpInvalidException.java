package org.se06203.besgtn.config.exception;


import org.se06203.besgtn.config.ErrorCode;

public class OtpInvalidException extends ServerException {

    // TODO: 8/6/2024 improve this hack
    public OtpInvalidException(String api) {
        super(switch (api) {
                    case "register" -> ErrorCode.OTP_CODE_INVALID_OR_EXPIRED;
                    case "resetPassword" -> ErrorCode.RESET_PASSWORD_OTP_CODE;
                    default -> ErrorCode.OTP_INVALID;
                }
        );
    }

    public OtpInvalidException() {
        super(ErrorCode.OTP_INVALID);
    }
}
