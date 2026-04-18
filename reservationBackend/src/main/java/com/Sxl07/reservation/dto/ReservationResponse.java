package com.Sxl07.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.Sxl07.reservation.entity.ReservationStatus;

/**
 * Response payload for reservation data.
 *
 * @param id reservation identifier
 * @param clientName client name
 * @param date reservation date
 * @param time reservation time
 * @param service reserved service
 * @param status current reservation status
 */
public record ReservationResponse(
        Long id,
        String clientName,
        LocalDate date,
        LocalTime time,
        String service,
        ReservationStatus status) {
}
