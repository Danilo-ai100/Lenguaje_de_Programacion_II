import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class EstudianteService {
  private apiUrl = '/api/estudiantes';

  constructor(private http: HttpClient) {}

  listarEstudiantes(dni?: string): Observable<any[]> {
    if (dni) {
      return this.http.get<any[]>(`${this.apiUrl}?dni=${dni}`);
    }
    return this.http.get<any[]>(this.apiUrl);
  }
  eliminarEstudiante(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  actualizarEstudiante(id: number, datos: any) {
    return this.http.put(`${this.apiUrl}/${id}`, datos);
  }
}
