package com.Sxl07.reservation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Sxl07.reservation.entity.Reservation;
import com.Sxl07.reservation.entity.ReservationStatus;
import com.Sxl07.reservation.exception.ReservationBusinessException;
import com.Sxl07.reservation.exception.ReservationNotFoundException;
import com.Sxl07.reservation.repository.ReservationRepository;

/**
 * Contains the business logic for reservation management.
 */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    /**
     * Creates a new reservation service with the required repository dependency.
     *
     * @param reservationRepository repository used to persist and query reservations
     */
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Retrieves all reservations in the system.
     *
     * @return all persisted reservations
     */
    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Creates a reservation only when no other reservation exists for the same date and time.
     * The reservation is always stored with status {@link ReservationStatus#ACTIVE}.
     *
     * @param reservation reservation data to persist
     * @return the saved reservation
     * @throws ReservationBusinessException if another reservation already exists at the same date and time
     */
    @Transactional
    public Reservation createReservation(Reservation reservation) {
        if (reservationRepository.existsByDateAndTime(reservation.getDate(), reservation.getTime())) {
            throw new ReservationBusinessException("A reservation already exists for the selected date and time.");
        }

        reservation.setStatus(ReservationStatus.ACTIVE);
        return reservationRepository.save(reservation);
    }

    /**
     * Cancels an existing reservation by its identifier.
     *
     * @param id identifier of the reservation to cancel
     * @return the updated reservation with status {@link ReservationStatus#CANCELLED}
     * @throws ReservationBusinessException if the reservation does not exist or is already canceled
     */
    @Transactional
    public Reservation cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found."));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new ReservationBusinessException("The reservation is already canceled.");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        return reservationRepository.save(reservation);
    }
}
