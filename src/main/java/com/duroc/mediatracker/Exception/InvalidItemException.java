package com.duroc.mediatracker.Exception;

public class InvalidItemException extends IllegalArgumentException{
    public InvalidItemException(String message) {
        super(message);
    }

}