package com.paddown.paddown.utils;

import java.util.Base64;
import java.util.UUID;


public class Base64EncodedUUID {
    public static String getBase64EncodedUUID(){
            UUID uuid = UUID.randomUUID();
            String uuidAsString = uuid.toString();
            return new String(Base64.getEncoder().encode(uuidAsString.getBytes()));
    }
}
