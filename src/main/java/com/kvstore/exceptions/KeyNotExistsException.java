package com.kvstore.exceptions;

/**
 * @author shubham.gupta
 */
public class KeyNotExistsException extends RuntimeException {
    private String message;
    public KeyNotExistsException(String message){
        this.message = message;
    }
}
