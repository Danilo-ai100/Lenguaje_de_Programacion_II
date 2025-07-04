import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class InstitucionService {
  private apiUrl = '/api/instituciones';

  constructor(private http: HttpClient) {}

  sugerencias(nombre: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/sugerencias?nombre=${encodeURIComponent(nombre)}`);
  }

  guardarInstitucion(data: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, data);
  }
}
