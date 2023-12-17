package com.paddown.paddown.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    public ResponseEntity<HttpStatus> authenticate(){
        return new ResponseEntity(HttpStatus.OK);
    }
}
