import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService:AuthService,private route:Router)
  {

  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      //cumplida esta condicion determina si deja pasar o no, utilizar el authguard es redundancia, con este seria suficiente
      if(this.authService.estaAutenticado())
      {
        if(this.tokenExpirado())
        {
          this.authService.logout();
          this.route.navigate(['/login']);
          return false;
        }
        {

        }
        return true;
      }
    this.route.navigate(['/login'])
    return false;
  }

  //metodo que verifica si el token expiro o no de acuerdo a el tiempo que tiene 
  tokenExpirado():boolean{
    let token = sessionStorage.getItem('token');
    let payload = this.authService.obtenerDatosToken(token);
    let fechaActual = new Date().getTime() /1000; //dividimos en mil para obtenerla en segundos
    if(payload.exp<fechaActual){
      return true;
    }
    return false;
  }
  
}
