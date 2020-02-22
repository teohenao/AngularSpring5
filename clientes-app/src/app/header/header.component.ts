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

    logout():void{
        this.authService.logout();
        Swal.fire('bye bye','hasta luego baby','info');
        this.router.navigate(['/login']);
    }
}