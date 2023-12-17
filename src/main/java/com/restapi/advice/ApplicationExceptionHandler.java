package com.restapi.advice;

import com.restapi.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.context.support.DefaultMessageSourceResolvable;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleInvalidArgument(MethodArgumentNotValidException ex){
        Map<String, Object> errorMap = new LinkedHashMap<>();
        errorMap.put("timestamp", new Date().toString());
        List<String> errors = ex.getBindingResult().getFieldErrors()          // Get all Field Errors
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        errorMap.put("errors", errors);
//        log.warn("Received request: BAD_REQUEST - Invalid Argument");
        return errorMap;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleUserNotFoundException(UserNotFoundException ex){
        Map<String, String> errorMap = new LinkedHashMap<>();
        errorMap.put("timestamp", new Date().toString());
        errorMap.put("status", "404");
        errorMap.put("errorMessage", ex.getMessage());
        errorMap.put("path", "/api/users/");
//        log.warn("Received request: USER_NOT_FOUND ");
        return errorMap;
    }
}
