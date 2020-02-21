import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from './usuario';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  //estos metodos se colocan con _ ya que se implementara su set y get por aparte buena practica
  private _usuario:Usuario;
  private _token:string;

  constructor(private http:HttpClient) { }

  //manejar las variables con _ y los get de esta manera simplifica la manera de llamarlos en los getter, se puede ver cuando inciia session y obtiene el usuario desde el session
  public get usuario():Usuario
  {
    if(this._usuario != null)
    {
      return this._usuario;
    }else if(this._usuario == null && sessionStorage.getItem('usuario')!=null)
    {
      //casteamos con as Usuario a tipo usuario y con parse a tipo json,
      this._usuario = JSON.parse(sessionStorage.getItem('usuario')) as Usuario;
      return this._usuario;
    }
    return new Usuario();
  }

  public get token():string
  {
    if(this._token != null)
    {
      return this._token;
    }else if(this._token==null && sessionStorage.getItem('token')!=null)
    {
      this._token = sessionStorage.getItem('token');
      return this._token;
    }
    return null;
  }

  login(usuario:Usuario):Observable<any>{
    const urlEndpoint = 'http://localhost:8080/oauth/token';

    //btoa es para convertir en base 64
    const credenciales = btoa('angularapp'+':'+'12345');

    const httpHeaders = new HttpHeaders({'Content-Type':'application/x-www-form-urlencoded',
  'Authorization':'Basic '+credenciales});

  let params = new URLSearchParams();
  params.set('grant_type','password');
  params.set('username',usuario.username);
  params.set('password',usuario.password);
  
  console.log(params.toString());

  return this.http.post<any>(urlEndpoint,params.toString(),{headers:httpHeaders})
  }

  guardarUsuario(accessToken:string):void{
    let objetoUser = this.obtenerDatosToken(accessToken);
    this._usuario = new Usuario();
    this._usuario.nombre = objetoUser.nombre;
    this._usuario.apellido = objetoUser.apellido;
    this._usuario.email = objetoUser.email;
    this._usuario.username= objetoUser.user_name;
    this._usuario.roles = objetoUser.authorities;

    //objeto global de js que nos permite guardar datos en el session storage del navegador
    //stringify convierte de json a string
    sessionStorage.setItem('usuario',JSON.stringify(this._usuario));

  }

  guardarToken(accessToken:string):void
  {
      this._token = accessToken;
      sessionStorage.setItem('token',this._token);
  }

  obtenerDatosToken(accessToken:string):any
  {
    if(accessToken!=null)
    {
      return JSON.parse(atob(accessToken.split(".")[1]));
    }
    return null;
  }
}