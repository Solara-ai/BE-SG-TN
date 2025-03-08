package org.se06203.besgtn.config.exception.handler;

import org.springframework.http.HttpStatus;

public interface BaseErrorCodeMsg {

    HttpStatus getHttpStatus();

    int getResultCode();

    String getResultMsg();
}