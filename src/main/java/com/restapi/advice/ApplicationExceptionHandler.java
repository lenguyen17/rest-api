package com.restapi.advice;

import com.restapi.exception.ExcelException;
import com.restapi.exception.RoleException;
import com.restapi.exception.UserAlreadyExistsException;
import com.restapi.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleInvalidArgument(MethodArgumentNotValidException ex){
        Map<String, Object> errorMap = new LinkedHashMap<>();
        errorMap.put("timestamp", new Date().toString());
        errorMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        List<String> errors = ex.getBindingResult().getFieldErrors()          // Get all Field Errors
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        errorMap.put("errors", errors);
        log.warn("Received request: BAD_REQUEST - Invalid Argument");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExcelException.class)
    public Map<String, String> handleExcelException(ExcelException ex){
        log.warn("Received request: BAD_REQUEST - Excel Exception");
        return getMapErrorMessage(ex, String.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleUserNotFoundException(UserNotFoundException ex) {
        log.warn("Received request: USER_NOT_FOUND");
        return getMapErrorMessage(ex, String.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public Map<String, String> handleUserNotFoundException(UserAlreadyExistsException ex) {
        log.warn("Received request: USER_ALREADY_EXISTS");
        return getMapErrorMessage(ex, String.valueOf(HttpStatus.CONFLICT.value()));
    }

    @ResponseStatus(HttpStatus.REQUEST_ENTITY_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Map<String, String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex){
        log.warn("Received request: MAX_UPLOAD_SIZE");
        return getMapErrorMessage(ex, String.valueOf(HttpStatus.REQUEST_ENTITY_TOO_LARGE.value()));

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RoleException.class)
    public Map<String, String> handleRoleException(RoleException ex){
        log.warn("Received request: BAD_ROLE_REQUEST");
        return getMapErrorMessage(ex, String.valueOf(HttpStatus.BAD_REQUEST.value()));
    }

    public Map<String, String> getMapErrorMessage(Exception ex,String status){
        Map<String, String> errorMap = new LinkedHashMap<>();
        errorMap.put("timestamp", new Date().toString());
        errorMap.put("status", status);
        errorMap.put("errorMessage", ex.getMessage());
        errorMap.put("path", "/api/users/");
        return errorMap;
    }


}
