import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { SharedSpinnerService } from './shared-spinner.service';

@Component({
  selector: 'app-spinner-overlay',
  standalone: true,
  imports: [CommonModule, MatProgressSpinnerModule],
  template: `
    <div *ngIf="spinner.loading$ | async" class="spinner-overlay">
      <mat-progress-spinner mode="indeterminate" color="primary"></mat-progress-spinner>
    </div>
  `,
  styles: [`
    .spinner-overlay {
      position: fixed;
      top: 0; left: 0; right: 0; bottom: 0;
      background: rgba(255,255,255,0.5);
      z-index: 9999;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  `]
})
export class SpinnerOverlayComponent {
  constructor(public spinner: SharedSpinnerService) {}
}
