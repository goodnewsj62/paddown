package com.paddown.paddown.utils;

import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Base64EncodedUUID {
    public String getBase64EncodedUUID(){
            UUID uuid = UUID.randomUUID();
            String uuidAsString = uuid.toString();
            return new String(Base64.getEncoder().encode(uuidAsString.getBytes()));
    }
}
