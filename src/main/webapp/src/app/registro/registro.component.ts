import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AlertaService } from '../services/alerta.service';
import { UsuarioService } from '../services/usuario.service';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit {

  registerForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private userService: UsuarioService,
      public alertaService: AlertaService,
  ) {
    
  }

  ngOnInit() {
      this.registerForm = this.formBuilder.group({
          dni: ['', Validators.required],
          password: ['', [Validators.required]],
          password2: ['', [Validators.required]],
          nombre: ['', [Validators.required]],
          apellidos: ['', Validators.required],
          correo: ['', Validators.required],
          telefono: ['', Validators.required]
          
      });
  }

  // getter para acceder a los controles del form
  get f() { return this.registerForm.controls; }

  onSubmit() {
      this.submitted = true;
      this.alertaService.clear();


     
      if ((this.f.dni.value.length != 9)) {
          this.alertaService.error("Formato de DNI incorrecto. El DNI debe de tener 8 números y una letra", false);
          return;
      }

      if ((this.f.password.value.length < 5)) {
          this.alertaService.error("Formato de contraseña incorrecta. La contraseña debe contener al menos 6 carácteres, mayúsuculas y minúsculas, números y algún símbolo.", false);
          return;
      }

      if (this.f.password.value != this.f.password2.value) {
        this.alertaService.error("Las contraseñas no coinciden.", false);
        return;
    }

      if ((this.f.telefono.value.length != 9) && (!isNaN(this.f.telefono.value))) {
        this.alertaService.error("Formato de número de teléfono incorrecto. El teléfono debe tener al menos 9 números", false);
        return;
    }

    if (isNaN(this.f.telefono.value)) {
        this.alertaService.error("Formato de número de teléfono incorrecto. El teléfono debe ser un número", false);
        return;
    }

    if (!(
        (this.f.correo.value.includes('@')) &&
        (this.f.correo.value.includes('.es') || this.f.correo.value.includes('.com')))
    ) {
        this.alertaService.error("Formato incorrecto del correo electrónico. ", false);
        return;
    }
      

      this.loading = true;
      this.userService.register(this.registerForm.value)
          .pipe(first())
          .subscribe(
              data => {
                this.alertaService.success('Registration successful', true);
                this.router.navigate(['/login'], { queryParams: { registered: true } });
              },
              error => {
                this.alertaService.error("Error: El usuario ya está registrado.");
                this.loading = false;
              });



     

  }


}

