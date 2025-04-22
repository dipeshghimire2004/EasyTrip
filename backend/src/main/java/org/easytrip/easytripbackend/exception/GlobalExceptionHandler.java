package org.easytrip.easytripbackend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(GuesthouseNotFoundException.class)
    public ResponseEntity<String> handleGuesthouseNotFound(GuesthouseNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedRoleException.class)
    public ResponseEntity<String> handleUnauthorizedRole(UnauthorizedRoleException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<String> handleFileStorageException(FileStorageException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    // Generic Exception Handler for All Runtime Exceptions
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
//        logger.error("RuntimeException: {}", ex.getMessage());
//
//        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Runtime Exception", ex.getMessage());
//    }
//
//    // Handle Specific Custom Exceptions
//    @ExceptionHandler({ UserNotFoundException.class, InvalidCredentialsException.class })
//    public ResponseEntity<Map<String, Object>> handleCustomExceptions(RuntimeException ex) {
//        HttpStatus status = (ex instanceof UserNotFoundException) ? HttpStatus.NOT_FOUND : HttpStatus.UNAUTHORIZED;
//
//        logger.warn("Custom Exception Occurred: {}", ex.getMessage());
//        return buildErrorResponse(status, ex.getClass().getSimpleName(), ex.getMessage());
//    }
//
//    // Catch any Unhandled Exception
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
//        logger.error("Unhandled Exception: {}", ex.getMessage());
//
//        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Error", ex.getMessage());
//    }
//
//    // Helper method to create a formatted error response
//    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String errorType, String message) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("timestamp", LocalDateTime.now());
//        response.put("status", status.value());
//        response.put("error", errorType);
//        response.put("message", message);
//
//        return new ResponseEntity<>(response, status);
//    }
//}