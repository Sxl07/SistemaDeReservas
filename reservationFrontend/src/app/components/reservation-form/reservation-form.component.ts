import { ChangeDetectionStrategy, ChangeDetectorRef, Component, DestroyRef, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { ReservationCreateRequest } from '../../models/reservation-create-request';
import { ReservationService } from '../../services/reservation.service';
import { ToastMessageComponent } from '../toast-message/toast-message.component';

/**
 * Provides a reactive form to create reservations.
 */
@Component({
  selector: 'app-reservation-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, ToastMessageComponent],
  templateUrl: './reservation-form.component.html',
  styleUrl: './reservation-form.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReservationFormComponent {
  private readonly formBuilder = inject(FormBuilder);
  private readonly reservationService = inject(ReservationService);
  private readonly router = inject(Router);
  private readonly destroyRef = inject(DestroyRef);
  private readonly changeDetectorRef = inject(ChangeDetectorRef);

  readonly serviceOptions = [
    'Laptop preventive maintenance',
    'Desktop cleaning and thermal paste replacement',
    'Operating system installation',
    'Hardware diagnostics and repair',
    'Data backup and recovery',
    'Virus and malware removal',
  ];

  readonly reservationForm = this.formBuilder.nonNullable.group({
    clientName: ['', [Validators.required]],
    date: ['', [Validators.required]],
    time: ['', [Validators.required]],
    service: ['', [Validators.required]],
  });

  submitting = false;
  toastVisible = false;
  toastMessage = '';

  /**
   * Submits the reservation form to create a reservation.
   */
  submitReservation(): void {
    if (this.reservationForm.invalid) {
      this.reservationForm.markAllAsTouched();
      return;
    }

    this.submitting = true;
    this.hideToast();
    this.changeDetectorRef.markForCheck();

    const reservationRequest: ReservationCreateRequest = this.reservationForm.getRawValue();

    this.reservationService
      .createReservation(reservationRequest)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.submitting = false;
          this.reservationForm.reset({
            clientName: '',
            date: '',
            time: '',
            service: '',
          });
          this.changeDetectorRef.markForCheck();
          void this.router.navigate(['/reservations']);
        },
        error: (error: { error?: { message?: string } }) => {
          this.submitting = false;
          this.toastMessage = error?.error?.message ?? 'Unable to save reservation. Please try again.';
          this.toastVisible = true;
          this.changeDetectorRef.markForCheck();
        },
      });
  }

  /**
   * Hides the toast notification.
   */
  hideToast(): void {
    this.toastVisible = false;
    this.changeDetectorRef.markForCheck();
  }

  /**
   * Returns whether a form control is invalid and touched.
   *
   * @param controlName control name to evaluate
   * @returns true when the control is invalid and touched
   */
  isInvalid(controlName: 'clientName' | 'date' | 'time' | 'service'): boolean {
    const control = this.reservationForm.controls[controlName];
    return control.touched && control.invalid;
  }
}
