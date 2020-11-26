import { Component, Input} from '@angular/core';
import { MatDialog} from '@angular/material/dialog'; 
import { MatTableDataSource} from '@angular/material/table';
import { UsuarioService } from '../services/usuario.service';
import { AuthService } from '../auth.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PerfilusuarioComponent } from '../perfilusuario/perfilusuario.component';
import { Router } from '@angular/router';

export interface DatosUsuario{
  contrasena: string;
  nombre: string;
  apellidos: string;
  dni: string;
  telefono: string;
  correo: string;
  tipo: string;
}

@Component({
  selector: 'app-usuarios-admin',
  templateUrl: './usuarios-admin.component.html',
  styleUrls: ['./usuarios-admin.component.css']
})

export class UsuariosAdminComponent{
  columnas: string[] = ['Dni','Nombre', 'Apellidos', 'Telefono', 'Correo', 'Tipo'];
  dataSource = new MatTableDataSource<DatosUsuario>();
  data: DatosUsuario[];
  usuario: any;
  @Input() public usuarios;
  constructor(
    private user: UsuarioService,
    private auth: AuthService,
    public dialog: MatDialog,
    private FormBuilder: FormBuilder,
    private router:Router
  ) { }

  ngOnInit(): void {
    this.user.getUsuarios()
    .subscribe((data: DatosUsuario[]) => {
      this.data = data;
      console.log(data)
      this.dataSource = new MatTableDataSource(data);
    });
  }



  modificar(el): void{
    const dialogRef = this.dialog.open(PerfilusuarioComponent, {
      width: '325px',
      data: el
    });
  }
  
  eliminar(dni): void{
    this.user.delete(dni)
    .subscribe(response => {
      this.usuario = response;
      this.ngOnInit();
  });

  }
}
