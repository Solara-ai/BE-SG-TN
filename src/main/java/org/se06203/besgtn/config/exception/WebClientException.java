package org.se06203.besgtn.config.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class WebClientException extends RuntimeException {

    private String url;
    private HttpStatus httpStatus;
    private transient Object errorAttributes;

    public WebClientException(String url, HttpStatus httpStatus, Object errorAttributes) {
        this.url = url;
        this.httpStatus = httpStatus;
        this.errorAttributes = errorAttributes;
    }
}
