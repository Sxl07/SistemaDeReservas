package com.Sxl07.reservation.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Sxl07.reservation.dto.ApiErrorResponse;

/**
 * Global REST exception handler for reservation endpoints.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Handles business rule violations.
     *
     * @param exception business exception thrown by the service layer
     * @return HTTP 409 with a descriptive error payload
     */
    @ExceptionHandler(ReservationBusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(ReservationBusinessException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiErrorResponse(exception.getMessage()));
    }

    /**
     * Handles reservation not found errors.
     *
     * @param exception not-found exception thrown by the service layer
     * @return HTTP 404 with a descriptive error payload
     */
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(ReservationNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiErrorResponse(exception.getMessage()));
    }

    /**
     * Handles request validation errors.
     *
     * @param exception validation exception produced by request body validation
     * @return HTTP 400 with the validation messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(message));
    }
}
