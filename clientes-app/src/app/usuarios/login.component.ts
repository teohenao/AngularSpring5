import { Component, OnInit } from '@angular/core';
import { Usuario } from './usuario';
import swal from 'sweetalert2'
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  titulo:string = 'Por favor inicie sesion';
  usuario:Usuario;

  constructor(private authService:AuthService,private router:Router) {
    this.usuario = new Usuario();
   }

  ngOnInit(): void {
  }

  login():void
  {
    console.log(this.usuario);
    if(this.usuario.username == null || this.usuario.password == null)
    {
      swal.fire('error login','user O password vacias','error')
      return;
    }
    this.authService.login(this.usuario).subscribe(response=>{
      console.log(response);
      this.router.navigate(['/clientes']);
      //split genera un areglo separando cada uno respecto al punto separa cada uno por el punto, con 1  tomamos la posicion  numero 1
      //atob sirve para convertir eso en json, pero aun es de tipo string hasta que se parsea
      let objetoPayload = JSON.parse(atob(response.access_token.split(".")[1]));
      console.log(objetoPayload);
      swal.fire('login','hola '+objetoPayload.user_name,'success');
    });
  }
}
