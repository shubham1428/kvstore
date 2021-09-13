package com.kvstore.exceptions;

/**
 * @author shubham.gupta
 */
public class TypeMismatchException extends RuntimeException {
    private String message;
    public TypeMismatchException(String message){
        this.message = message;
    }
}
