package com.Sxl07.reservation.exception;

/**
 * Exception thrown when a reservation business rule is violated.
 */
public class ReservationBusinessException extends RuntimeException {

    /**
     * Creates a new business exception with the given message.
     *
     * @param message detail message describing the business rule violation
     */
    public ReservationBusinessException(String message) {
        super(message);
    }
}
