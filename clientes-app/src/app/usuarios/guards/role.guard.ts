import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth.service';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private authService:AuthService,private route:Router)
  {

  }
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      //es necesario preguntar si inicio sesison, y si tiene el rol
      if(!this.authService.estaAutenticado()){
        this.route.navigate(['/login']);
        return false;
      }
      let role = next.data['role'] as string;
      if(this.authService.hasRole(role)){
        return true;
      }
    Swal.fire('Acceso denegado','usted que hace por aca!','error');
    this.route.navigate(['/clientes']);
    return false;
  }
  
}
