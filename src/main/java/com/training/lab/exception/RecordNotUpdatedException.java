package com.training.lab.exception;

public class RecordNotUpdatedException extends Exception{
    public RecordNotUpdatedException() {
        super();
    }

    public RecordNotUpdatedException(String message) {
        super(message);
    }

    public RecordNotUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
