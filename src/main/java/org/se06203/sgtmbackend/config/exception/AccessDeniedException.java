package org.se06203.sgtmbackend.config.exception;


import org.se06203.sgtmbackend.config.ErrorCode;

public class AccessDeniedException extends ServerException {
   public AccessDeniedException(ErrorCode errorCode) {
       super(errorCode);
   }
}
