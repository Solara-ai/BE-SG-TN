package org.se06203.besgtn.config.exception;

import lombok.Getter;
import org.se06203.besgtn.config.ErrorCode;

@Getter
public class SocialUserNotFoundException extends ServerException {
    public SocialUserNotFoundException(String payload) {
        super(ErrorCode.NOT_FOUND, payload);
    }
}
