import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  currentUserSubject: any;
  constructor(private http: HttpClient) { }

  register(user) {
    return this.http.post(`${environment.apiUrl}`, user);
}

delete(id) {
    return this.http.delete(`${environment.apiUrl}/deleteUser/${id}`);
}
update(user,id) {
    return this.http.put(`${environment.apiUrl}/update/${id}`, user);
}
updateadmin(user,id){
  return this.http.put(`${environment.apiUrl}/admin-update/${id}`, user);
}
getUsuarios(){
  return this.http.get(`${environment.apiUrl}/admin-usuarios`);
}

findUser(id){
  return this.http.get(`${environment.apiUrl}/perfil/${id}`);
}

registeradmin(user) {
  return this.http.post(`${environment.apiUrl}/adminregistro`, user);
}
}
