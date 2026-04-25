export type ReservationStatus = 'ACTIVE' | 'CANCELLED';

export interface Reservation {
    id: number;
    clientName: string;
    date: string;
    time: string;
    service: string;
    status: ReservationStatus;
}
