import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../auth.service';
import { PerfilusuarioComponent } from '../perfilusuario/perfilusuario.component';
import { ReunionComponent } from '../reunion/reunion.component';
import { UsuarioService } from '../services/usuario.service';


@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})


export class MenuComponent implements OnInit {
  public nombre: String;
  public admin: boolean;
  private reunion: any;
  constructor(
    private UsuarioService: AuthService,
    private userservice: UsuarioService,
    public dialog: MatDialog) { }

  ngOnInit(): void {
    this.nombre = this.UsuarioService.currentUserValue[0].nombre;
    if (this.UsuarioService.currentUserValue[0].tipo == "NJlGkLOGjTQ") {
      this.admin = true;
    }
    else {
      this.admin = false;
    }
    this.reunion = {
      titulo: [''],
      descripcion: [''],
      organizador: this.UsuarioService.currentUserValue[0].nombre,
      fecha: [''],
      horaIni: [''],
      horaFin: [''],
      listaAsistentes: ['']
     };
  }

  verPerfil(){
   
    this.userservice.findUser(this.UsuarioService.currentUserValue[0].dni)
    .subscribe(response => {
      const dialogRef = this.dialog.open(PerfilusuarioComponent, {
        width: '450px',
        data: response
      });
    });
    
  }
  crearReunion(){

    this.reunion.action = "Crear"
    const dialogRef = this.dialog.open(ReunionComponent, {
      width: '450px',
      data: this.reunion
		});
  }

  logout() {
    this.UsuarioService.logout();
  }

}
