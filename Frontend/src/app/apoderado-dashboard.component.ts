
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { CelularAdminComponent } from './celular-admin.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-apoderado-dashboard',
  templateUrl: './apoderado-dashboard.component.html',
  styleUrls: ['./apoderado-dashboard.component.scss'],
  standalone: true,
  imports: [CommonModule, MatCardModule, MatDividerModule, CelularAdminComponent, RouterModule],
})
export class ApoderadoDashboardComponent {}