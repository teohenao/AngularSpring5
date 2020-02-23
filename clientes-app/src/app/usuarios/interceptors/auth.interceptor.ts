import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Injectable } from '@angular/core';
import { AuthService } from '../auth.service';
import { catchError } from 'rxjs/operators';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

//interceptor para manejar errores en el response, en el token se maneja el request
@Injectable()
export class AuthInterceptor implements HttpInterceptor
{
    constructor(private authService:AuthService,private router:Router)
    {

    }
    intercept(req:HttpRequest<any>,next:HttpHandler):
    Observable<HttpEvent<any>>{

        return next.handle(req).pipe(
            catchError(e=>{
            if(e.status == 401){
                //preguntamos si el token expiro, si expiro entonces cerramos
                if(this.authService.estaAutenticado()){
                Swal.fire("Termino su session","vuelva ingrese por favor",'error');
                this.authService.logout();
                this.router.navigate(['/login'])
                }
            this.router.navigate(['/login'])
        }
        else if(e.status == 403){
            Swal.fire("Acceso Denegado","que hace por aca ?",'error');
            this.router.navigate(['/clientes'])
            }
            return throwError(e);
        })
      );
    }
}