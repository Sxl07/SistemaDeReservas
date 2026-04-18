package com.Sxl07.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sxl07.reservation.entity.Reservation;

/**
 * Repository for persistence operations related to reservations.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Checks whether a reservation already exists for the given date and time.
     *
     * @param date reservation date to verify
     * @param time reservation time to verify
     * @return {@code true} if at least one reservation exists at the given date and time; otherwise {@code false}
     */
    boolean existsByDateAndTime(LocalDate date, LocalTime time);
}
