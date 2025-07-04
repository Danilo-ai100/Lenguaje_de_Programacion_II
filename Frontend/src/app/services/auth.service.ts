import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = '/api/auth/login';
  private userKey = 'user';

  constructor(private http: HttpClient) {}


  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.apiUrl, { username, password }).pipe(
      tap(user => {
        if (user && user.rol && typeof window !== 'undefined' && window.localStorage) {
          localStorage.setItem(this.userKey, JSON.stringify(user));
        }
      })
    );
  }


  logout() {
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.removeItem(this.userKey);
    }
  }


  getUser() {
    if (typeof window !== 'undefined' && window.localStorage) {
      const user = localStorage.getItem(this.userKey);
      return user ? JSON.parse(user) : null;
    }
    return null;
  }

  isLoggedIn(): boolean {
    return !!this.getUser();
  }

  getRol(): string | null {
    const user = this.getUser();
    return user ? user.rol : null;
  }
}
