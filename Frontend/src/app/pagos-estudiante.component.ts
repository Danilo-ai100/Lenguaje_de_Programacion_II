import { Component, Input, OnInit } from '@angular/core';
import { FilePreviewPipe } from './file-preview.pipe';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PagoService } from './services/pago.service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-pagos-estudiante',
  templateUrl: './pagos-estudiante.component.html',
  styleUrls: ['./pagos-estudiante.component.scss'],
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatDividerModule, MatChipsModule, FilePreviewPipe],
})
export class PagosEstudianteComponent implements OnInit {
  estudianteId!: number;
  pagos: any[] = [];
  fileToUpload: File | null = null;
  pagoSeleccionado: any = null;

  constructor(private pagoService: PagoService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.estudianteId = +id;
        this.cargarPagos();
      }
    });
  }

  cargarPagos() {
    this.pagoService.pagosPorEstudiante(this.estudianteId).subscribe((data: any[]) => {
      this.pagos = data;
    });
  }

  seleccionarPago(pago: any) {
    this.pagoSeleccionado = pago;
  }

  handleFileInput(event: any) {
    this.fileToUpload = event.target.files[0];
  }

  enviarComprobante() {
    if (this.pagoSeleccionado && this.fileToUpload) {
      this.pagoService.subirComprobante(this.pagoSeleccionado.id, this.fileToUpload).subscribe(() => {
        alert('Comprobante enviado');
        this.pagoSeleccionado = null;
        this.fileToUpload = null;
        this.cargarPagos();
      });
    }
  }

  cancelarEnvio() {
    this.pagoSeleccionado = null;
    this.fileToUpload = null;
  }
}
