
import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AuthService } from '../auth.service';
import { AlertaService } from '../services/alerta.service';
import { UsuarioService } from '../services/usuario.service';



export interface UserData {
  contrasena: string;
  nombre: string;
  apellidos: string;
  dni: string;
  telefono: string;
  correo: string;
  tipo: string;
}

@Component({
  selector: 'app-perfilusuario',
  templateUrl: './perfilusuario.component.html',
  styleUrls: ['./perfilusuario.component.css']
})
export class PerfilusuarioComponent implements OnInit {
  perfilForm: FormGroup;
  loading = false;
  submitted = false;
  Modificar = "Modificar";
  local_data: any;
  tipo: any;
  admin: boolean;
  modificado: boolean
  form: any;



  constructor(public dialogRef: MatDialogRef<PerfilusuarioComponent>,
    private auth: AuthService,
    public alertaService: AlertaService,
    private router: Router,
    private userservice: UsuarioService,
    @Inject(MAT_DIALOG_DATA) public data: UserData


  ) {
    this.local_data = { ...data };
    console.log("tipo: " + this.local_data.tipo)

  }
  ngOnInit(): void {
 this.modificado = false;
    
    
    if (this.auth.currentUserValue[0].tipo == "NJlGkLOGjTQ") {

      this.admin = true
    } else {
      this.admin = false
    }
    if (this.local_data.tipo == "admin") {
      this.tipo = "admin"

    } else {
      this.tipo = "asistente"

    }

    console.log("tipo: " + this.local_data.tipo)




  }

  closeDialog() {
    this.dialogRef.close({ event: 'Cancel' });
    this.router.navigate(['/carga']);
  }

  changeGender(e) {
    this.local_data.tipo = e.target.value;
    console.log("metodo: " + e.target.value);

  }

  guardar() {

    this.submitted = true;
    this.alertaService.clear();


    if ((this.local_data.contrasena.length < 5)) {
      this.alertaService.error("Formato de contraseña incorrecta. La contraseña debe contener al menos 6 carácteres, mayúsuculas y minúsculas, números y algún símbolo.", false);
      return 0;
    }

   

    if ((this.local_data.telefono.length != 9) && (!isNaN(this.local_data.telefono))) {
      this.alertaService.error("Formato de número de teléfono incorrecto. El teléfono debe tener al menos 9 números", false);
      return 0;
    }

    if (isNaN(this.local_data.telefono)) {
      this.alertaService.error("Formato de número de teléfono incorrecto. El teléfono debe ser un número", false);
      return 0;
    }

    if (!(
      (this.local_data.correo.includes('@')) &&
      (this.local_data.correo.includes('.es') || this.local_data.correo.includes('.com')))
    ) {
      this.alertaService.error("Formato incorrecto del correo electrónico. ", false);
      return 0;
    }
    this.local_data.tipo = this.tipo;

    this.loading = true;
    if (this.admin)
      this.userservice.updateadmin(this.local_data, this.auth.currentUserValue[0].dni)
        .pipe(first())
        .subscribe(
          data => {
            this.alertaService.success('Modificacion realizada', true);
            console.log(this.local_data)
          });
    else {
      this.userservice.update(this.local_data, this.auth.currentUserValue[0].dni)
        .pipe(first())
        .subscribe(
          data => {
            this.alertaService.success('Modificacion realizada', true);
            console.log(this.local_data)
          });
    }
    return 1
  }

  modificar() {

    if (this.Modificar == "Guardar") {

      if (this.guardar() == 1){
        this.Modificar = "Modificar";
        this.modificado = false;
        this.habilitar(true);
        this.dialogRef.disableClose = false;
      }
    } else {
      this.Modificar = "Guardar";
      this.modificado = true;
      this.dialogRef.disableClose = true;
      this.habilitar(false);
      
    }
  }




  cancelar() {
    this.closeDialog();
  }

  private habilitar(bolean): void { 
    this.form = document.getElementById("correo");
    this.form.disabled = bolean;
    this.form = document.getElementById("nombre");
    this.form.disabled = bolean;
    this.form = document.getElementById("apellidos");
    this.form.disabled = bolean;
    this.form = document.getElementById("contrasena");
    this.form.disabled = bolean;
    this.form = document.getElementById("telefono");
    this.form.disabled = bolean;
    this.form = document.getElementById("cancelar");
    this.form.disabled = bolean;
    this.form = document.getElementById("admin");
    this.form.disabled = bolean;
    this.form = document.getElementById("asistente");
    this.form.disabled = bolean;
   
  }
}
