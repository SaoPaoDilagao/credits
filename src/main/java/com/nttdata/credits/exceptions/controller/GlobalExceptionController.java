package com.nttdata.credits.exceptions.controller;

import com.nttdata.credits.exceptions.CustomInformationException;
import com.nttdata.credits.exceptions.CustomNotFoundException;
import com.nttdata.credits.exceptions.CustomResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionController {
    @ExceptionHandler
    public ResponseEntity<CustomResult> handle(CustomInformationException ex) {
        CustomResult customResult = new CustomResult();
        customResult.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(customResult);
    }

    @ExceptionHandler
    public ResponseEntity<CustomResult> handle(CustomNotFoundException ex) {
        CustomResult customResult = new CustomResult();
        customResult.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(customResult);
    }
}
