import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';

/**
 * Displays feedback messages in a toast-style notification.
 */
@Component({
  selector: 'app-toast-message',
  standalone: true,
  imports: [],
  templateUrl: './toast-message.component.html',
  styleUrl: './toast-message.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ToastMessageComponent {
  @Input() message = '';
  @Input() variant: 'error' | 'success' = 'error';
  @Input() visible = false;

  @Output() close = new EventEmitter<void>();

  /**
   * Emits an event to request toast closure.
   */
  closeToast(): void {
    this.close.emit();
  }
}
