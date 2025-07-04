import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AdminService {
  private apiUrl = '/api/admin';

  constructor(private http: HttpClient) {}

  getCelular(): Observable<string> {
    return this.http.get(`${this.apiUrl}/celular`, { responseType: 'text' });
  }

  crearAdmin(data: { username: string; nombreId: string }): Observable<any> {
    return this.http.post('/api/auth/crear-admin', data);
  }

  listarUsuariosClasificados(): Observable<any> {
    return this.http.get('/api/usuarios');
  }

  eliminarAdmin(id: number): Observable<any> {
    return this.http.delete(`/api/usuarios/admin/${id}`);
  }

  eliminarApoderado(id: number): Observable<any> {
    return this.http.delete(`/api/usuarios/apoderado/${id}`);
  }

  editarAdmin(id: number, data: any): Observable<any> {
    return this.http.put(`/api/usuarios/admin/${id}`, data);
  }

  editarApoderado(id: number, data: any): Observable<any> {
    return this.http.put(`/api/usuarios/apoderado/${id}`, data);
  }
}
