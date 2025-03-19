package org.se06203.besgtn.config.exception.handler;

import org.springframework.http.HttpStatus;

public interface BaseErrorCodeMsg {

    int getHttpStatus();

    HttpStatus getResultCode();

    String getResultMsg();
}