import { Component } from '@angular/core';
import { AuthService } from '../usuarios/auth.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
    selector:'app-header',
    templateUrl:'./header.component.html'
})
export class HeaderComponent
{

    //cuando se utiliza {{}} en el html se llama interpolacion de string
    title:string = 'AppAngular-Spring';

    constructor(private authService:AuthService,private router:Router){

    }
    logout(): void {
        let username = this.authService.usuario.username;
        this.authService.logout();
        Swal.fire('Logout', `Hola ${username}, has cerrado sesión con éxito!`, 'success');
        this.router.navigate(['/login']);
      }
}