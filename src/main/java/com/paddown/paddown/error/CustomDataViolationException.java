package com.paddown.paddown.error;

public class CustomDataViolationException  extends RuntimeException {
    public CustomDataViolationException(){
        super("Data Integrity Violation");
    }
    public CustomDataViolationException(String message){
        super(message);
    }
}
