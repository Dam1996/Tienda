package com.ejercicio.tienda.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exceptions.class})
    protected ResponseEntity<Object> handleConflict(Exceptions exceptions, WebRequest webRequest){
        String bodyOfResponse = exceptions.getMessage();
        return handleExceptionInternal(exceptions,bodyOfResponse,new HttpHeaders(),exceptions.getHttpStatus(),webRequest);
    }
}
