package com.training.lab.exception;

public class RecordNotCreatedException extends Exception{
    public RecordNotCreatedException() {
        super();
    }

    public RecordNotCreatedException(String message) {
        super(message);
    }

    public RecordNotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
