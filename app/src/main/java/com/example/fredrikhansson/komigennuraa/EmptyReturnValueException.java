package com.example.fredrikhansson.komigennuraa;

public class EmptyReturnValueException extends Exception {
    public EmptyReturnValueException() { super(); }
    public EmptyReturnValueException(String message) { super(message); }
    public EmptyReturnValueException(String message, Throwable cause) { super(message, cause); }
    public EmptyReturnValueException(Throwable cause) { super(cause); }
}