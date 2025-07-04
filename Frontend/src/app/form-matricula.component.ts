import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { InstitucionService } from './services/institucion.service';
import { MatriculaService } from './services/matricula.service';
import { PdfService } from './services/pdf.service';
import { debounceTime, switchMap } from 'rxjs/operators';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-form-matricula',
  templateUrl: './form-matricula.component.html',
  styleUrls: ['./form-matricula.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
    MatButtonModule,
    MatDividerModule
  ],
})
export class FormMatriculaComponent implements OnInit {
  form: FormGroup;
  traslado: boolean = false;
  sugerencias: any[] = [];
  showInstitucionExtra: boolean = false;

  constructor(
    private fb: FormBuilder,
    private institucionService: InstitucionService,
    private matriculaService: MatriculaService,
    private pdfService: PdfService
  ) {
    this.form = this.fb.group({
      // Estudiante
      nombreCompleto: ['', Validators.required],
      apellidoPaterno: ['', Validators.required],
      apellidoMaterno: ['', Validators.required],
      anioAcademico: [2025, Validators.required],
      grado: ['', Validators.required],
      seccion: ['', Validators.required],
      fechaNacimiento: ['', Validators.required],
      dniEstudiante: ['', [Validators.required, Validators.pattern(/^\d{8}$/)]],
      traslado: [false],
      institucionProcedencia: [''],
      nivelProcedencia: [''],
      codigoModular: [''],
      // Apoderado
      nombreCompletoApoderado: ['', Validators.required],
      apellidosCompletosApoderado: ['', Validators.required],
      dniApoderado: ['', [Validators.required, Validators.pattern(/^\d{8}$/)]],
      celularApoderado: ['', Validators.required],
      // Contacto Emergencia
      nombreContactoEmergencia: [''],
      celularContactoEmergencia: ['']
    });
  }

  ngOnInit() {
    this.form.get('traslado')?.valueChanges.subscribe(val => {
      this.showInstitucionExtra = val;
      const institucionControls = ['institucionProcedencia', 'nivelProcedencia', 'codigoModular'];
      institucionControls.forEach(ctrl => {
        if (val) {
          this.form.get(ctrl)?.setValidators([Validators.required]);
        } else {
          this.form.get(ctrl)?.clearValidators();
          this.form.get(ctrl)?.setValue('');
        }
        this.form.get(ctrl)?.updateValueAndValidity();
      });
    });
    this.form.get('institucionProcedencia')?.valueChanges
      .pipe(
        debounceTime(300),
        switchMap(nombre =>
          nombre ? this.institucionService.sugerencias(nombre) : []
        )
      )
      .subscribe(sugs => this.sugerencias = sugs);
  }

  onSelectSugerencia(sug: any) {
    this.form.patchValue({
      institucionProcedencia: sug.nombre,
      nivelProcedencia: sug.nivel,
      codigoModular: sug.codigoModular
    });
    this.sugerencias = [];
  }

  guardarInstitucionExtra() {
    const { institucionProcedencia, nivelProcedencia, codigoModular } = this.form.value;
    this.institucionService.guardarInstitucion({
      nombre: institucionProcedencia,
      nivel: nivelProcedencia,
      codigoModular: codigoModular
    }).subscribe((sug: any) => this.onSelectSugerencia(sug));
  }

  submit() {
    if (this.form.valid) {
      this.matriculaService.registrarMatricula(this.form.value).subscribe({
        next: (resp: any) => {
          let msg = 'Matrícula registrada correctamente.';
          if (resp && resp.passwordGenerada) {
            msg += `\n\nUsuario: ${this.form.value.dniApoderado}\nContraseña: ${resp.passwordGenerada}\n\nGuarde estos datos para ingresar al sistema.`;
          }
          alert(msg);
          this.pdfService.generarMatriculaPDF(this.form.value);
          this.form.reset({ anioAcademico: 2025, traslado: false });
        },
        error: (err) => {
          if (err.error && err.error.error) {
            alert('Error: ' + err.error.error);
          } else {
            alert('Ocurrió un error inesperado al registrar la matrícula.');
          }
        }
      });
    } else {
      alert('Por favor, complete todos los campos obligatorios antes de registrar.');
    }
  }
}
