package com.raksul.userApi.controllers;

import com.raksul.userApi.constants.Constants;
import com.raksul.userApi.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class BaseController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        return new ResponseEntity(new ErrorDto(Constants.VALIDATION_ERROR + e.getBindingResult().getFieldError().getField()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<String> generalException(Exception e) {
        return new ResponseEntity(new ErrorDto(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
