import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from './services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-layout',
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.scss'],
  standalone: true,
  imports: [CommonModule, RouterModule],
})
export class MainLayoutComponent {
  user: any;
  menu: any[] = [];

  constructor(private auth: AuthService, private router: Router) {
    this.user = this.auth.getUser();
    this.setMenu();
  }

  setMenu() {
    if (!this.user) return;
    if (this.user.rol === 'ADMIN') {
      this.menu = [
        { label: 'Dashboard', route: '/admin/dashboard' },
        { label: 'Matricular', route: '/admin/matricula' },
        { label: 'Estudiantes', route: '/admin/estudiantes' },
        { label: 'Validación de Pagos', route: '/admin/validacion-pagos' },
        { label: 'Estadística de Pagos', route: '/admin/estadisticas' }
      ];
    } else if (this.user.rol === 'PADRE') {
      this.menu = [
        { label: 'Pagos', route: '/apoderado/pagos' }
      ];
    }
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
