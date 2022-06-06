package com.paypay.Exception;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.paypay.dto.Response.Response;

@ControllerAdvice
public class GlobalHandler {
    private Response response;
    private Map<String, Object> errors;

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<?> nullAttribute(BadRequestException e) {
        response = new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        errors = new HashMap<>();

        exception.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        response = new Response(HttpStatus.BAD_REQUEST.value(), exception.getFieldError().getDefaultMessage(), errors);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
