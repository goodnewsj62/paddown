package com.paddown.paddown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.paddown.paddown.error.BadInputException;
import com.paddown.paddown.error.CustomDataViolationException;
import com.paddown.paddown.error.EntityNotFound;
import com.paddown.paddown.error.ErrorResponse;

@ControllerAdvice
public class PaddownExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler({EntityNotFound.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEntityNotFound( Exception ex){
        ErrorResponse errResp = new ErrorResponse(Arrays.asList(ex.getMessage()), "NOT_FOUND",  404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResp);
    }

    @ExceptionHandler({BadInputException.class})
    public ResponseEntity<ErrorResponse> handleBadInputError( Exception ex){
        ErrorResponse errResp = new ErrorResponse(Arrays.asList(ex.getMessage()), "BAD_REQUEST",  400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResp);
    }


    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(EmptyResultDataAccessException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList("Cannot delete non-existing resource"));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList("Data Integrity Violation: we cannot process your request."));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(CustomDataViolationException.class)
    public ResponseEntity<ErrorResponse> handleCustomDataIntegrityException(Exception ex) {
        ErrorResponse errResp = new ErrorResponse(Arrays.asList(ex.getMessage()), "BAD_REQUEST",  400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResp);
    }
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledException(Exception ex) {
        ErrorResponse errResp = new ErrorResponse(Arrays.asList(ex.getMessage()), "BAD_REQUEST",  400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResp);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList("Oops an un handled error occurred"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
            List<String> errors =  new ArrayList<>();
            ex.getBindingResult().getAllErrors().forEach((error)->errors.add(error.getDefaultMessage()) );
            ErrorResponse errResp = new ErrorResponse(errors, "BAD_REQUEST",  400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResp);
    }
}
