
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EstudianteService } from './services/estudiante.service';
import { AuthService } from './services/auth.service';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-lista-estudiantes',
  templateUrl: './lista-estudiantes.component.html',
  styleUrls: ['./lista-estudiantes.component.scss'],
  standalone: true,
  imports: [CommonModule, MatCardModule, MatTableModule, MatButtonModule, MatDialogModule, ReactiveFormsModule],
})
export class ListaEstudiantesComponent implements OnInit {
  estudiantes: any[] = [];
  formEdicion: FormGroup;
  estudianteEditando: any = null;
  dialogRef: any;

  constructor(
    private estudianteService: EstudianteService,
    private auth: AuthService,
    private router: Router,
    private fb: FormBuilder,
    private dialog: MatDialog
  ) {
    this.formEdicion = this.fb.group({
      nombreCompleto: ['', Validators.required],
      apellidoPaterno: ['', Validators.required],
      apellidoMaterno: ['', Validators.required],
      anioAcademico: ['', Validators.required],
      grado: ['', Validators.required],
      seccion: ['', Validators.required],
      dni: ['', Validators.required],
      traslado: [false],
      institucionProcedencia: ['']
    });
  }

  get esApoderado(): boolean {
    const user = this.auth.getUser();
    return user && user.rol === 'PADRE';
  }

  ngOnInit() {
    const user = this.auth.getUser();
    if (user && user.rol === 'PADRE') {
      this.estudianteService.listarEstudiantes(user.username).subscribe((data: any[]) => {
        this.estudiantes = data;
      });
    } else {
      this.estudianteService.listarEstudiantes().subscribe((data: any[]) => {
        this.estudiantes = data;
      });
    }
  }
  verPagos(est: any) {
    this.router.navigate(['/apoderado/pagos', est.id]);
  }

  editarEstudiante(est: any) {
    this.estudianteEditando = est;
    this.formEdicion.patchValue({
      nombreCompleto: est.nombreCompleto,
      apellidoPaterno: est.apellidoPaterno,
      apellidoMaterno: est.apellidoMaterno,
      anioAcademico: est.anioAcademico,
      grado: est.grado,
      seccion: est.seccion,
      dni: est.dni,
      traslado: est.traslado,
      institucionProcedencia: est.institucionProcedencia
    });
    (document.getElementById('modal-edicion') as any).showModal();
  }

  guardarEdicion() {
    if (this.formEdicion.valid && this.estudianteEditando) {
      const datos = { ...this.estudianteEditando, ...this.formEdicion.value };
      this.estudianteService.actualizarEstudiante(this.estudianteEditando.id, datos).subscribe(() => {
        Object.assign(this.estudianteEditando, this.formEdicion.value);
        this.estudianteEditando = null;
        (document.getElementById('modal-edicion') as any).close();
        alert('Estudiante actualizado correctamente.');
      });
    }
  }

  cancelarEdicion() {
    this.estudianteEditando = null;
    (document.getElementById('modal-edicion') as any).close();
  }

  eliminarEstudiante(est: any) {
    if (confirm('¿Seguro que deseas eliminar al estudiante ' + est.nombreCompleto + '?')) {
      this.estudianteService.eliminarEstudiante(est.id).subscribe(() => {
        this.estudiantes = this.estudiantes.filter(e => e.id !== est.id);
        alert('Estudiante eliminado correctamente.');
      });
    }
  }
}
