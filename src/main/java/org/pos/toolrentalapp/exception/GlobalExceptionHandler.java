package org.pos.toolrentalapp.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        //Error: Duplicate entry tool code
        return ResponseEntity.badRequest().body(ex.getLocalizedMessage());
    }


    @ExceptionHandler(ToolsException.class)
    public ResponseEntity<String> handleDToolsExceptionn(ToolsException ex) {
        return ResponseEntity.internalServerError().body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getLocalizedMessage());
    }

    // Handles field validations (@Min, @Max . . ..)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList());
    }


    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException ex) {
        ToolsException exception=new ToolsException("Provide date as: MM/dd/yyyy",ex);
        System.out.println("Invoked+++++++++++++++++++");
        return ResponseEntity.badRequest().body(exception.getLocalizedMessage());
    }

}

