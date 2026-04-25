package com.Sxl07.reservation.controller;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Sxl07.reservation.dto.ReservationRequest;
import com.Sxl07.reservation.dto.ReservationResponse;
import com.Sxl07.reservation.entity.Reservation;
import com.Sxl07.reservation.service.ReservationService;

/**
 * REST controller for reservation management.
 */
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from the Angular frontend
@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * Creates a controller with the required service dependency.
     *
     * @param reservationService business service used to manage reservations
     */
    

    /**
     * Lists all reservations.
     *
     * @return HTTP 200 with the list of reservations
     */
    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> reservations = reservationService.getAllReservations().stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(reservations);
    }

    /**
     * Creates a new reservation.
     *
     * @param request reservation data to create
     * @return HTTP 201 with the created reservation
     */
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        Reservation savedReservation = reservationService.createReservation(toEntity(request));
        ReservationResponse response = toResponse(savedReservation);

        return ResponseEntity.created(URI.create("/reservas/" + savedReservation.getId())).body(response);
    }

    /**
     * Cancels an existing reservation.
     *
     * @param id identifier of the reservation to cancel
     * @return HTTP 200 with the canceled reservation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable Long id) {
        Reservation canceledReservation = reservationService.cancelReservation(id);
        return ResponseEntity.ok(toResponse(canceledReservation));
    }

    private Reservation toEntity(ReservationRequest request) {
        return new Reservation(null, request.clientName(), request.date(), request.time(), request.service(), null);
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getClientName(),
                reservation.getDate(),
                reservation.getTime(),
                reservation.getService(),
                reservation.getStatus());
    }
}
