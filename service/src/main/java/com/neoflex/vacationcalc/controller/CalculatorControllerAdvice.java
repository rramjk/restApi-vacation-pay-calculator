package com.neoflex.vacationcalc.controller;

import com.neoflex.vacationcalc.exceptions.BadRequestException;
import com.neoflex.vacationcalc.exceptions.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.OffsetDateTime;

import static com.neoflex.vacationcalc.exceptions.ErrorCode.PARAMETER_OF_REQUEST_IS_INCORRECT;
import static com.neoflex.vacationcalc.exceptions.ErrorCode.PARAMETER_OF_REQUEST_IS_MISSING;

@RestControllerAdvice
public class CalculatorControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .distinct()
                .collect(java.util.stream.Collectors.joining("; "));

        return buildError(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), message, request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingParameterException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                PARAMETER_OF_REQUEST_IS_MISSING.getErrorMessage(ex.getParameterName()),
                request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                PARAMETER_OF_REQUEST_IS_INCORRECT.getErrorMessage(ex.getName()),
                request);
    }

    private ResponseEntity<ErrorResponseDto> buildError(HttpStatus status, String error, String message, HttpServletRequest request) {
        String path = request.getRequestURI();
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error(error)
                .message(message)
                .path(path)
                .build();
        return ResponseEntity.status(status).body(errorResponseDto);
    }

}
