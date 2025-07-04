import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class MatriculaService {
  private apiUrl = '/api/matricula';

  constructor(private http: HttpClient) {}

  registrarMatricula(data: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, data);
  }
}
