import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SharedSnackbarService } from './shared-snackbar.service';
import { SharedSpinnerService } from './shared-spinner.service';
import { EstadisticasService } from './services/estadisticas.service';

@Component({
  selector: 'app-estadisticas-pagos',
  templateUrl: './estadisticas-pagos.component.html',
  styleUrls: ['./estadisticas-admin.component.scss'],
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatSnackBarModule],
})
export class EstadisticasPagosComponent {
  totalValidados: number | null = null;

  constructor(
    private snackbar: SharedSnackbarService,
    private spinner: SharedSpinnerService,
    private estadisticasService: EstadisticasService
  ) {}

  cargarTotalValidados() {
    this.spinner.show();
    this.estadisticasService.totalValidados().subscribe({
      next: (total) => {
        this.totalValidados = total;
        this.spinner.hide();
      },
      error: () => {
        this.snackbar.show('Error al cargar el monto total', 'Cerrar', 4000);
        this.spinner.hide();
      }
    });
  }
}
