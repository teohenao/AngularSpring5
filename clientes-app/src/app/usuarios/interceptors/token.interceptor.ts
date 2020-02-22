import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { AuthService } from '../auth.service';

//clase que se encarga de interfceptar las peticiones http para no pasar en cada una el token, si no de forma mas global, es buena practica
@Injectable()
export class TokenInterceptor implements HttpInterceptor
{
    constructor(private authService:AuthService)
    {

    }
    intercept(req:HttpRequest<any>,next:HttpHandler):
    Observable<HttpEvent<any>>{
        //de aca se puede obtener el token de la clase pero por el error que me salio pailas
        let token = sessionStorage.getItem('token');
        if(token !=null)
        {
            const authReq = req.clone({
                headers:req.headers.set('Authorization','Bearer '+token)
            });
            return next.handle(authReq);
        }
        //lo que hace next es ir al proximo interceptor hasta terminar
        return next.handle(req);
    }
}