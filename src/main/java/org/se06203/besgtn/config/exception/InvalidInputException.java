package org.se06203.besgtn.config.exception;

import lombok.Getter;

@Getter
public class InvalidInputException extends RuntimeException {
    private final String message;

    public InvalidInputException(String message) {
        super(new Throwable());
        this.message = message;
    }
}
