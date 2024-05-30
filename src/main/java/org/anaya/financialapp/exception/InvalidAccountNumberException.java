package org.anaya.financialapp.exception;

public class InvalidAccountNumberException extends RuntimeException{
    public InvalidAccountNumberException(String message) {
        super(message);
    }

    public InvalidAccountNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAccountNumberException(Throwable cause) {
        super(cause);
    }

    public InvalidAccountNumberException() {
        super();
    }
}
