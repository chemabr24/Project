import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReunionesService {
  

  private uri = 'http://localhost:9000/AgendaE5';

  constructor(
    private http: HttpClient
  ) {}


  public findAll(){
    return this.http.get(this.uri + '/Reuniones');
  }

  public findReuniones(dni){
    return this.http.get(`${environment.apiUrl}/Reuniones/${dni}`);
  }
  
  public cancelarReunion(dni,id){
  
    return this.http.post(`${environment.apiUrl}/CancelarReuniones/${dni}`,id);
  }
  public modificarReunion(reunion,dni) {
    console.log(reunion)
    return this.http.post(`${environment.apiUrl}/ModificarReunion/${dni}`, reunion);
  }
  public crearReunion(reunion, dni){
    return this.http.post(`${environment.apiUrl}/CrearReunion/${dni}`, reunion);
  }

}