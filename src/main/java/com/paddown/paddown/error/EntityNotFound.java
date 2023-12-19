package com.paddown.paddown.error;

public class EntityNotFound extends RuntimeException {
    public EntityNotFound(String id,  Class<?> entity){
        super( entity.getSimpleName().toLowerCase() + " " +  id +  " does not exists");
    }
}
