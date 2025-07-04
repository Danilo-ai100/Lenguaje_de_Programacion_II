
import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth.service';


@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}
  canActivate(): boolean | UrlTree {
    if (!this.auth.isLoggedIn()) {
      return this.router.createUrlTree(['/login']);
    }
    return true;
  }
}


@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}
  canActivate(): boolean | UrlTree {
    if (!this.auth.isLoggedIn() || this.auth.getRol() !== 'ADMIN') {
      return this.router.createUrlTree(['/login']);
    }
    return true;
  }
}


@Injectable({ providedIn: 'root' })
export class ApoderadoGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}
  canActivate(): boolean | UrlTree {
    if (!this.auth.isLoggedIn() || this.auth.getRol() !== 'PADRE') {
      return this.router.createUrlTree(['/login']);
    }
    return true;
  }
}
