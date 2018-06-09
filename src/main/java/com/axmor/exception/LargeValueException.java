package com.axmor.exception;

public class LargeValueException extends RuntimeException{
    public LargeValueException() {
    }

    public LargeValueException(String message) {
        super(message);
    }
}
