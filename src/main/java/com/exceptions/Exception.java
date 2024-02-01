package com.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dto.response.ApiRespErrorDto;


@ControllerAdvice 
public class Exception extends ResponseEntityExceptionHandler { 
    @Override 
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, 
                                                                  HttpStatus status, WebRequest request) { 
        List<String> errors = ex.getBindingResult().getFieldErrors().stream() 
                .map(DefaultMessageSourceResolvable::getDefaultMessage) 
                .collect(Collectors.toList()); 
        ApiRespErrorDto apiErrorResponse = new ApiRespErrorDto(HttpStatus.BAD_REQUEST, String.join(" ", errors), errors); 
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST); 
    } 
}
