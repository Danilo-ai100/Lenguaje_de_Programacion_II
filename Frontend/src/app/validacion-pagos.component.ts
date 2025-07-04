import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PagoService } from './services/pago.service';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-validacion-pagos',
  templateUrl: './validacion-pagos.component.html',
  styleUrls: ['./validacion-pagos.component.scss'],
  standalone: true,
  imports: [CommonModule, MatCardModule, MatTableModule, MatButtonModule, MatDividerModule, MatChipsModule],
})
export class ValidacionPagosComponent implements OnInit {
  pagosPendientes: any[] = [];
  pagoSeleccionado: any = null;

  constructor(private pagoService: PagoService) {}

  ngOnInit() {
    this.cargarPagos();
  }

  cargarPagos() {
    this.pagoService.pagosPendientes().subscribe((data: any[]) => {
      this.pagosPendientes = data;
    });
  }

  seleccionarPago(pago: any) {
    this.pagoSeleccionado = pago;
  }


  getComprobanteUrl(urlArchivo: string): string {
    if (!urlArchivo) return '';
    // Extraer solo el nombre del archivo
    const filename = urlArchivo.split(/[/\\]/).pop();
    return `/api/pagos/archivo/${filename}`;
  }

  descargarArchivo(url: string) {
    window.open(url, '_blank');
  }

  validar(valido: boolean) {
    if (this.pagoSeleccionado) {
      this.pagoService.validarPago(this.pagoSeleccionado.id, valido).subscribe(() => {
        this.pagoSeleccionado = null;
        this.cargarPagos();
      });
    }
  }

  cancelar() {
    this.pagoSeleccionado = null;
  }
}
