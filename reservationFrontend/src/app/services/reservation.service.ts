import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ReservationCreateRequest } from '../models/reservation-create-request';
import { Reservation } from '../models/reservation';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/reservas`;

  /**
   * Retrieves all reservations from the backend API.
   *
   * @returns an observable with the reservation list
   */
  getAllReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.baseUrl);
  }

  /**
   * Creates a new reservation in the backend API.
   *
   * @param reservation reservation payload to create
   * @returns an observable with the created reservation
   */
  createReservation(reservation: ReservationCreateRequest): Observable<Reservation> {
    return this.http.post<Reservation>(this.baseUrl, reservation);
  }

  /**
   * Cancels an existing reservation.
   *
   * @param id reservation identifier to cancel
   * @returns an observable with the updated reservation
   */
  cancelReservation(id: number): Observable<Reservation> {
    return this.http.delete<Reservation>(`${this.baseUrl}/${id}`);
  }
}
