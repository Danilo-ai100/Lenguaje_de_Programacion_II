import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class EstadisticasService {
  private apiUrl = '/api/estadisticas';

  constructor(private http: HttpClient) {}

  totalValidados(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/total-validados`);
  }
}
