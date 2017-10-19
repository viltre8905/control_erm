package com.verynet.gcint.api.exceptions;

/**
 * Created by day on 18/09/2016.
 */
public class InvalidDirectoryException extends RuntimeException {
    public static final long serialVersionUID = 12121212L;

    public InvalidDirectoryException() {
    }

    public InvalidDirectoryException(String message) {
        super(message);
    }

    public InvalidDirectoryException(Throwable cause) {
        super(cause);
    }

    public InvalidDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
