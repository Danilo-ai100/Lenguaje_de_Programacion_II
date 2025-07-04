import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService } from './services/admin.service';

@Component({
  selector: 'app-celular-admin',
  template: `<div class="celular-admin">
    <b>¿Dudas? Contacta al administrador:</b>
    <span *ngIf="celular">{{ celular }}</span>
  </div>`,
  styles: [`.celular-admin { margin: 2rem 0; color: #1976d2; font-size: 1.1rem; text-align: center; }`],
  standalone: true,
  imports: [CommonModule],
  // No es necesario providers aquí, el servicio ya está providedIn: 'root'
})
export class CelularAdminComponent implements OnInit {
  celular: string = '';
  constructor(private adminService: AdminService) {}
  ngOnInit() {
    this.adminService.getCelular().subscribe((cel: string) => this.celular = cel);
  }
}
