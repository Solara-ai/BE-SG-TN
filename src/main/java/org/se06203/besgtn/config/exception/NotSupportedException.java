package org.se06203.besgtn.config.exception;

import lombok.Getter;
import org.se06203.besgtn.config.ErrorCode;

@Getter
public class NotSupportedException extends ServerException {
    public NotSupportedException() {
        super(ErrorCode.NOT_SUPPORTED);
    }
}
