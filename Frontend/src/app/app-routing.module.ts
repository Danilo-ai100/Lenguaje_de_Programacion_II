import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login.component';
import { FormMatriculaComponent } from './form-matricula.component';
import { ListaEstudiantesComponent } from './lista-estudiantes.component';
import { PagosEstudianteComponent } from './pagos-estudiante.component';
import { ValidacionPagosComponent } from './validacion-pagos.component';
import { EstadisticasAdminComponent } from './estadisticas-admin.component';
import { MainLayoutComponent } from './main-layout.component';
import { AuthGuard, AdminGuard, ApoderadoGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  {
    path: 'admin',
    canActivate: [AdminGuard],
    component: MainLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: EstadisticasAdminComponent },
      { path: 'matricula', component: FormMatriculaComponent },
      { path: 'estudiantes', component: ListaEstudiantesComponent },
      { path: 'validacion-pagos', component: ValidacionPagosComponent },
      { path: 'estadisticas', component: EstadisticasAdminComponent }
    ]
  },
  {
    path: 'apoderado',
    canActivate: [ApoderadoGuard],
    component: MainLayoutComponent,
    children: [
      { path: '', redirectTo: 'pagos', pathMatch: 'full' },
      { path: 'pagos', component: PagosEstudianteComponent },
      { path: 'matricula', component: FormMatriculaComponent }
    ]
  },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
