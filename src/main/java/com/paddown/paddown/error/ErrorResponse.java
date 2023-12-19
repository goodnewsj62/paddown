package com.paddown.paddown.error;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ErrorResponse {
    private int code;
    
    private String status;
    
    private List<String>  message;

    @JsonFormat(shape=JsonFormat.Shape.STRING,  pattern = "yyy-MM-dd'T'hh:mm:ss")
    private LocalDateTime timestamp;


    public ErrorResponse(){
        timestamp =  LocalDateTime.now();
    }

    public ErrorResponse( List<String> message){
        this();
        this.message =  message;
    }

    public ErrorResponse( List<String> message,  String status){
        this();
        this.message =  message;
        this.status =  status;
    }


    public ErrorResponse( List<String> message,  String status,  int code){
        this();
        this.message =  message;
        this.status =  status;
        this.code =  code;
    }



}
