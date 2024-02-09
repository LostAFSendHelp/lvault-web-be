package com.example.lostaf.lvaultweb.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.lostaf.lvaultweb.exceptions.BadRequestException;
import com.example.lostaf.lvaultweb.exceptions.DataNotFoundException;
import com.example.lostaf.lvaultweb.models.Result;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public @ResponseBody Result handleDataNotFound() {
        return Result.builder()
            .isSuccess(false)
            .message("Data cannot be found")
            .statusCode(HttpStatus.NOT_FOUND)
            .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public @ResponseBody Result handleBadResult() {
        return Result.builder()
            .isSuccess(false)
            .message("Invalid request data")
            .statusCode(HttpStatus.BAD_REQUEST)
            .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody Result handleArbitraryError() {
        return Result.builder()
            .isSuccess(false)
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
    }
}
