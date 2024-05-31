package org.anaya.financialapp.exception;

public class InvalidClientException extends RuntimeException {
    public InvalidClientException(String message) {
        super(message);
    }

    public InvalidClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidClientException(Throwable cause) {
        super(cause);
    }

    public InvalidClientException() {
        super();
    }
}
