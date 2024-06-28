package com.mgs.card_generator.controller;

import java.util.HashMap;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.mgs.card_generator.dto.ErrorDTO;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElement(NoSuchElementException e) {
        return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(e.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidArgument(MethodArgumentNotValidException e) {
        
        HashMap<String, Object> map = new HashMap<>();
        map.put(e.getFieldError().getField(), e.getFieldError().getRejectedValue());
        
        ErrorDTO errorMessage = new ErrorDTO(
            HttpStatus.BAD_REQUEST,
            e.getFieldError().getDefaultMessage(),
            map);
            
        return ResponseEntity.badRequest().body(errorMessage.toString());
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidArgument(MethodArgumentTypeMismatchException e) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(e.getPropertyName(), e.getValue());

        ErrorDTO errorMessage = new ErrorDTO(
            HttpStatus.BAD_REQUEST,
            "Parâmetro de url inválido",
            map);

        return ResponseEntity.badRequest().body(errorMessage.toString());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidArgument(HttpMessageNotReadableException e) {
        ErrorDTO errorMessage = new ErrorDTO(
            HttpStatus.BAD_REQUEST,
            "Valor passado no corpo da requisição não condiz com a entrada esperada"
            );

        return ResponseEntity.badRequest().body(errorMessage.toString());
    }
    
}
