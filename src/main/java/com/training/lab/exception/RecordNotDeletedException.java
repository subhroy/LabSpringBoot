package com.training.lab.exception;

public class RecordNotDeletedException extends Exception{
    public RecordNotDeletedException() {
        super();
    }

    public RecordNotDeletedException(String message) {
        super(message);
    }

    public RecordNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
