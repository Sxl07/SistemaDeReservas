package com.Sxl07.reservation.exception;

/**
 * Exception thrown when a reservation cannot be found.
 */
public class ReservationNotFoundException extends RuntimeException {

    /**
     * Creates a new not-found exception with the given message.
     *
     * @param message detail message describing the missing reservation
     */
    public ReservationNotFoundException(String message) {
        super(message);
    }
}