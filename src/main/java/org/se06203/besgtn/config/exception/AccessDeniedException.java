package org.se06203.besgtn.config.exception;

import org.se06203.besgtn.config.ErrorCode;

public class AccessDeniedException extends ServerException {
    public AccessDeniedException() {
        super(ErrorCode.ACCESS_DENIED);
    }
}
