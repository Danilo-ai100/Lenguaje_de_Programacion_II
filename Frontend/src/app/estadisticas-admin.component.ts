// ...existing code...
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService } from './services/admin.service';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from './confirm-dialog.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SharedSnackbarService } from './shared-snackbar.service';
import { SharedSpinnerService } from './shared-spinner.service';
import { EstadisticasService } from './services/estadisticas.service';

@Component({
  selector: 'app-estadisticas-admin',
  templateUrl: './estadisticas-admin.component.html',
  styleUrls: ['./estadisticas-admin.component.scss'],
  standalone: true,
  imports: [CommonModule, MatCardModule, MatDividerModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatIconModule, MatSnackBarModule],
})
export class EstadisticasAdminComponent {
  totalValidados: number | null = null;
  formAdmin: FormGroup;
  resultadoCreacion: any = null;
  usuarios: any = null;
  mostrarUsuarios = false;
  usuarioEditando: any = null;
  editForm: FormGroup;

  constructor(
    private adminService: AdminService,
    private fb: FormBuilder,
    private dialog: MatDialog,
    private snackbar: SharedSnackbarService,
    private spinner: SharedSpinnerService,
    private estadisticasService: EstadisticasService
  ) {
    this.formAdmin = this.fb.group({
      username: ['', [Validators.required, Validators.email, Validators.pattern(/@gmail\.com$/)]],
      nombreId: ['', Validators.required]
    });
    this.editForm = this.fb.group({
      nombreId: ['', Validators.required],
      username: ['', Validators.required],
      nombreCompleto: ['']
    });
  }

  crearAdmin() {
    if (this.formAdmin.valid) {
      this.spinner.show();
      this.adminService.crearAdmin(this.formAdmin.value).subscribe({
        next: (resp) => {
          this.resultadoCreacion = resp;
          this.formAdmin.reset();
          this.snackbar.show('Administrador creado correctamente');
          this.spinner.hide();
        },
        error: (err) => {
          this.resultadoCreacion = { error: err.error };
          this.snackbar.show('Error al crear administrador', 'Cerrar', 4000);
          this.spinner.hide();
        }
      });
    }
  }

  verUsuarios() {
    this.spinner.show();
    this.adminService.listarUsuariosClasificados().subscribe({
      next: (data) => {
        this.usuarios = data;
        this.mostrarUsuarios = true;
        this.spinner.hide();
      },
      error: () => {
        this.snackbar.show('Error al cargar usuarios', 'Cerrar', 4000);
        this.spinner.hide();
      }
    });
  }

  cerrarUsuarios() {
    this.mostrarUsuarios = false;
  }

  editarUsuario(usuario: any) {
    this.usuarioEditando = usuario;
    this.editForm.patchValue({
      nombreId: usuario.nombreId,
      username: usuario.username,
      nombreCompleto: usuario.nombreCompleto || ''
    });
  }

  guardarEdicion() {
    if (!this.usuarioEditando) return;
    const data = this.editForm.value;
    this.spinner.show();
    if (this.usuarioEditando.rol === 'ADMIN') {
      this.adminService.editarAdmin(this.usuarioEditando.id, { nombreId: data.nombreId, username: data.username }).subscribe({
        next: () => {
          this.verUsuarios();
          this.usuarioEditando = null;
          this.snackbar.show('Administrador editado correctamente');
          this.spinner.hide();
        },
        error: () => {
          this.snackbar.show('Error al editar administrador', 'Cerrar', 4000);
          this.spinner.hide();
        }
      });
    } else {
      this.adminService.editarApoderado(this.usuarioEditando.id, { nombreId: data.nombreId, dni: data.username, nombreCompleto: data.nombreCompleto }).subscribe({
        next: () => {
          this.verUsuarios();
          this.usuarioEditando = null;
          this.snackbar.show('Apoderado editado correctamente');
          this.spinner.hide();
        },
        error: () => {
          this.snackbar.show('Error al editar apoderado', 'Cerrar', 4000);
          this.spinner.hide();
        }
      });
    }
  }

  cancelarEdicion() {
    this.usuarioEditando = null;
  }

  eliminarUsuario(usuario: any) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: 'Confirmar eliminación',
        message: `¿Seguro que deseas eliminar la cuenta de ${usuario.nombreId}?`
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinner.show();
        if (usuario.rol === 'ADMIN') {
          this.adminService.eliminarAdmin(usuario.id).subscribe({
            next: () => {
              this.verUsuarios();
              this.snackbar.show('Administrador eliminado correctamente');
              this.spinner.hide();
            },
            error: () => {
              this.snackbar.show('Error al eliminar administrador', 'Cerrar', 4000);
              this.spinner.hide();
            }
          });
        } else {
          this.adminService.eliminarApoderado(usuario.id).subscribe({
            next: () => {
              this.verUsuarios();
              this.snackbar.show('Apoderado eliminado correctamente');
              this.spinner.hide();
            },
            error: () => {
              this.snackbar.show('Error al eliminar apoderado', 'Cerrar', 4000);
              this.spinner.hide();
            }
          });
        }
      }
    });
  }

  cargarTotalValidados() {
    this.spinner.show();
    this.estadisticasService.totalValidados().subscribe({
      next: (total) => {
        this.totalValidados = total;
        this.spinner.hide();
      },
      error: () => {
        this.snackbar.show('Error al cargar el monto total', 'Cerrar', 4000);
        this.spinner.hide();
      }
    });
  }
}
