import { ChangeDetectionStrategy, ChangeDetectorRef, Component, DestroyRef, OnInit, inject } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { Reservation } from '../../models/reservation';
import { ReservationService } from '../../services/reservation.service';

/**
 * Displays and manages the reservation list.
 */
@Component({
  selector: 'app-reservation-table',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './reservation-table.component.html',
  styleUrl: './reservation-table.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReservationTableComponent implements OnInit {
  private readonly reservationService = inject(ReservationService);
  private readonly destroyRef = inject(DestroyRef);
  private readonly changeDetectorRef = inject(ChangeDetectorRef);

  reservations: Reservation[] = [];
  loading = false;
  errorMessage = '';
  cancelingIds = new Set<number>();

  /**
   * Loads the reservation table data when the component starts.
   */
  ngOnInit(): void {
    this.loadReservations();
  }

  /**
   * Loads all reservations from the backend.
   */
  loadReservations(): void {
    this.loading = true;
    this.errorMessage = '';
    this.changeDetectorRef.markForCheck();

    this.reservationService
      .getAllReservations()
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        finalize(() => {
          this.loading = false;
          this.changeDetectorRef.markForCheck();
        }),
      )
      .subscribe({
        next: (reservations) => {
          this.reservations = reservations;
          this.changeDetectorRef.markForCheck();
        },
        error: () => {
          this.errorMessage = 'Unable to load reservations. Please try again.';
          this.changeDetectorRef.markForCheck();
        },
      });
  }

  /**
   * Cancels the reservation identified by the given id.
   *
   * @param id reservation identifier
   */
  cancelReservation(id: number): void {
    if (this.cancelingIds.has(id)) {
      return;
    }

    this.cancelingIds.add(id);
    this.changeDetectorRef.markForCheck();

    this.reservationService.cancelReservation(id).pipe(takeUntilDestroyed(this.destroyRef)).subscribe({
      next: (updatedReservation: Reservation) => {
        this.reservations = this.reservations.map((reservation) =>
          reservation.id === id ? updatedReservation : reservation,
        );
        this.changeDetectorRef.markForCheck();
      },
      error: () => {
        this.errorMessage = 'Unable to cancel reservation. Please try again.';
        this.changeDetectorRef.markForCheck();
      },
      complete: () => {
        this.cancelingIds.delete(id);
        this.changeDetectorRef.markForCheck();
      },
    });
  }

  /**
   * Returns whether the reservation can be canceled.
   *
   * @param reservation reservation item from the list
   * @returns true when cancellation is allowed
   */
  canCancel(reservation: Reservation): boolean {
    return reservation.status !== 'CANCELLED' && !this.cancelingIds.has(reservation.id);
  }

}
