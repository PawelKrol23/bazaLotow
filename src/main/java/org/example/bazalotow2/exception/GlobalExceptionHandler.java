package org.example.bazalotow2.exception;

import org.example.bazalotow2.dto.ErrorDTO;
import org.example.bazalotow2.exception.notfound.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleNotFoundException(EntityNotFoundException exception,
                                                            WebRequest request) {
        return new ResponseEntity<>(
                new ErrorDTO(
                        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        exception.getMessage(),
                        request.getDescription(false)
                ),
                HttpStatus.NOT_FOUND
        );
    }
}
