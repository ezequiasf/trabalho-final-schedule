package com.dbccompany.trabalhofinalmod5.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + ": " + x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(CaloriesLimitExceededException.class)
    public ResponseEntity<Object> handleException(CaloriesLimitExceededException exception,
                                                  HttpServletRequest request) {
        return badRequest(exception);
    }

    @ExceptionHandler(PriceExpensiveException.class)
    public ResponseEntity<Object> handleException(PriceExpensiveException exception,
                                                  HttpServletRequest request) {
        return badRequest(exception);
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Object> handleException(RecipeNotFoundException exception,
                                                  HttpServletRequest request) {
        return badRequest(exception);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleException(UserAlreadyExistsException exception,
                                                  HttpServletRequest request) {
        return badRequest(exception);
    }

    @ExceptionHandler(UserDontExistException.class)
    public ResponseEntity<Object> handleException(UserDontExistException exception,
                                                  HttpServletRequest request) {
        return badRequest(exception);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<Object> handleException(IllegalAccessException exception,
                                                  HttpServletRequest request) {
        return badRequest(exception);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException exception,
                                                  HttpServletRequest request) {
        return badRequest(exception);
    }

    private ResponseEntity<Object> badRequest(Exception e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
