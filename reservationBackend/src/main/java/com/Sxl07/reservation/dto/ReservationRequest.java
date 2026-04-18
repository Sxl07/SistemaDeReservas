package com.Sxl07.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a reservation.
 *
 * @param clientName client name
 * @param date reservation date
 * @param time reservation time
 * @param service reserved service
 */
public record ReservationRequest(
        @NotBlank(message = "Client name is required.") String clientName,
        @NotNull(message = "Date is required.") LocalDate date,
        @NotNull(message = "Time is required.") LocalTime time,
        @NotBlank(message = "Service is required.") String service) {
}
