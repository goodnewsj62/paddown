package com.paddown.paddown.error;

public class StorageException extends RuntimeException {
    public StorageException(){
        super("Storage exception occurred");
    }
    
    public StorageException(String message){
        super(message);
    }
    public StorageException(String message, Throwable cause){
        super(message, cause);
    }

}
