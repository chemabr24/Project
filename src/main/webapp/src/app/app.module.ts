import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { ReunionesComponent } from './reuniones/reuniones.component';
import { ReunionesPreComponent } from './reuniones-pre/reuniones-pre.component';
import { MatDialogModule } from '@angular/material/dialog'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule,HTTP_INTERCEPTORS } from '@angular/common/http';
import { MenuComponent } from './menu/menu.component';
import { RegistroComponent } from './registro/registro.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ReunionComponent } from './reunion/reunion.component';
import { FlexLayoutModule } from "@angular/flex-layout";
import { AlertaComponent } from './alerta/alerta.component';
import { UsuariosAdminComponent } from './usuarios-admin/usuarios-admin.component';
import { MatTableModule } from '@angular/material/table';
import { PerfilusuarioComponent } from './perfilusuario/perfilusuario.component';
import { AuthInterceptorService } from './interceptor/auth-interceptor.service';
import { CommonModule } from '@angular/common';
import { CargaComponent } from './carga/carga.component';
import { RegistrarAdminComponent } from './registrar-admin/registrar-admin.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ReunionesComponent,
    ReunionesPreComponent,
    MenuComponent,
    RegistroComponent,
    ReunionComponent,
    AlertaComponent,
    UsuariosAdminComponent,
    PerfilusuarioComponent,
    CargaComponent,
    RegistrarAdminComponent
    

  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatTableModule
  ],
  entryComponents:[
    ReunionComponent,
    PerfilusuarioComponent
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
