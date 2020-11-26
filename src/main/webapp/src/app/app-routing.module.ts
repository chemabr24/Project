import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CargaComponent } from './carga/carga.component';
import { LoginComponent } from './login/login.component';
import { RegistrarAdminComponent } from './registrar-admin/registrar-admin.component';
import { RegistroComponent } from './registro/registro.component';
import { ReunionesComponent } from './reuniones/reuniones.component';
import { UsuariosAdminComponent } from './usuarios-admin/usuarios-admin.component';


const routes: Routes = [
  {
    path:'',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: 'reuniones', component: ReunionesComponent
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'register', component: RegistroComponent
  },
  {
    path: 'usuarios', component: UsuariosAdminComponent
  },
  {
    path: 'carga', component: CargaComponent
  },
  {
    path: 'registro-admin', component: RegistrarAdminComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
