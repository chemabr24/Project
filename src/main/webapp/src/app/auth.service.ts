import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';


@Injectable({ providedIn: 'root' })
export class AuthService {
    private currentUserSubject: BehaviorSubject<any>;
    public currentUser: Observable<any>;
    private tokenSubject: BehaviorSubject<any>;
    public token: Observable<any>;
    private uri = 'http://localhost:9000/AgendaE5';
    
    constructor(private http: HttpClient, private router: Router) {
        this.currentUserSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
        this.tokenSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('token')));
        this.token = this.tokenSubject.asObservable();
    }

    public get currentUserValue() {
        return this.currentUserSubject.value;
    }

    login(dni, password) {
        const params = new HttpParams()
            .set('dni', dni)
            .set('password', password);
        return this.http.get(this.uri+'/usuarios', { params : params })
            .pipe(map(token => {
                // almacena detalles del usuario y el token jwt en el almacenamiento local para mantener al usuario logueado entre refrescos de página
                localStorage.setItem('currentUser', JSON.stringify(token));
                this.currentUserSubject.next(token);
                return token[0];
            }));
    }

    /*
        login(dni, password) {
            return this.http.post<any>(${environment.apiUrl}/auth/login, { dni, password })
                .pipe(map(user => {
                    // almacena detalles del usuario y el token jwt en el almacenamiento local para mantener al usuario logueado entre refrescos de página
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    this.currentUserSubject.next(user);
                    return user;
                }));
        }
    */
    public logout() {
        // elimina al usuario del almacenamiento local y marca el usuario actual como nulo
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
        this.router.navigate(['/']);
    }
}