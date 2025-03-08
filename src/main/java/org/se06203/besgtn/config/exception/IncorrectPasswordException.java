package org.se06203.besgtn.config.exception;

import org.se06203.besgtn.config.ErrorCode;

public class IncorrectPasswordException extends ServerException {

    public IncorrectPasswordException(int cnt) {
        super(
                switch (cnt) {
                    case 1 -> ErrorCode.WRONG_PW_ATTEMPT_1;
                    case 2 -> ErrorCode.WRONG_PW_ATTEMPT_2;
                    case 3 -> ErrorCode.WRONG_PW_ATTEMPT_3;
                    case 4 -> ErrorCode.WRONG_PW_ATTEMPT_4;
                    case 5 -> ErrorCode.WRONG_PW_ATTEMPT_5;
                    default -> ErrorCode.FAILED;
                });
    }
}
