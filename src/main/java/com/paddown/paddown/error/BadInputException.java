package com.paddown.paddown.error;

public class BadInputException extends RuntimeException {
    public BadInputException(){
        super("Bad input data");
    }
    public BadInputException(String message){
        super(message);
    }
}
