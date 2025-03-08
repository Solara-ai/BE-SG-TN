package org.se06203.besgtn.config.exception;

import org.se06203.besgtn.config.ErrorCode;

public class MaxRecordExceededException extends ServerException {
    public MaxRecordExceededException() {
        super(ErrorCode.WARNING_LIMIT_RECORD);
    }
}
