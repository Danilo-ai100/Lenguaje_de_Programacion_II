import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PagoService {
  private apiUrl = '/api/pagos';

  constructor(private http: HttpClient) {}

  pagosPorEstudiante(estudianteId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/estudiante/${estudianteId}`);
  }

  subirComprobante(pagoId: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiUrl}/subir-comprobante/${pagoId}`, formData);
  }

  pagosPendientes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/pendientes`);
  }

  validarPago(pagoId: number, valido: boolean): Observable<any> {
    return this.http.post(`${this.apiUrl}/validar/${pagoId}?valido=${valido}`, {});
  }
}
