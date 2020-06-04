package com.endava.starbux;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String DRINK_NOT_FOUND = "Drink not found";
    private static final String BAD_PARAMETER_VALUE = "Bad parameter value";
    private static final String BAD_REQUEST_PAYLOAD = "BAD request payload";

    @ExceptionHandler(DrinkNotFoundException.class)
    public ResponseEntity<ErrorResponse> onDrinkNotFound(HttpServletRequest request,
                                                         DrinkNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), DRINK_NOT_FOUND,
                        e.getMessage(), request.getServletPath()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> onMethodArgumentTypeMismatch(HttpServletRequest request,
                                                                      MethodArgumentTypeMismatchException e) {
        String message = "Parameter: " + e.getName() + " has invalid value: " + e.getValue();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), BAD_PARAMETER_VALUE,
                        message, request.getServletPath()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> onMethodArgumentNotValidException(HttpServletRequest request,
                                                                           MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String message = makeErrorMessage(fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST_PAYLOAD,
                        message, request.getServletPath()));
    }

    private String makeErrorMessage(List<FieldError> fieldErrors) {
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append("Filed: ")
                    .append(error.getField())
                    .append(" has invalid value: ")
                    .append(error.getRejectedValue())
                    .append(",");
        }
        return builder.substring(0, builder.length() - 1);
    }
}
