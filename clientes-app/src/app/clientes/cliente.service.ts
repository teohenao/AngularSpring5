import { Injectable } from '@angular/core';
import { Cliente } from './cliente.js';
import { Observable, of,throwError } from 'rxjs';
import {HttpClient, HttpHeaders, HttpRequest, HttpEvent} from '@angular/common/http';
import {map,catchError,tap} from 'rxjs/operators';
import Swal from 'sweetalert2';
import {DatePipe} from '@angular/common';
import {Router} from '@angular/router'
import { Region } from './region.js';
import { AuthService } from '../usuarios/auth.service.js';


@Injectable()
export class ClienteService {

  private urlEndPoint:string = 'http://localhost:8080/api/clientes';

  private httpHeaders = new HttpHeaders({'Content-Type':'Application/json'});

  constructor(private http:HttpClient,private router:Router,private authService:AuthService) { }

  private agregarAutorizacion()
  {
    let token = sessionStorage.getItem('token');
    console.log("el token es en agregar autorizacion : "+token);
    console.log("el token es en el service : "+this.authService.obtenerToken())
    if(token != null)
    {
      //append es por que httpheaders es unmutable entonces crea un nueva instancia
      return this.httpHeaders.append('Authorization','Bearer '+token.toString());
    }
    return this.httpHeaders;
  }

  getRegiones():Observable<Region[]>{
    return this.http.get<Region[]>(this.urlEndPoint+'/regiones',{headers:this.agregarAutorizacion()}).pipe(
      catchError(e =>{
        this.isNoAutorizado(e);
        return throwError(e);
      })
    );
  }


  //observable y observadores, se subscriben a este cambio, es el patron de diseño observador
  //es para mantener atento a los cambios en el servidor y mantiene avisando a los cambios
  //any por que gracias al paginador nos devuelve un json con varios objetos y page es el numero de pagina
  getClientes(page:number):Observable<any>
  {
    //of para observables reactivos
    //return of(CLIENTES);
    
    //<CLienre[]> es un casteo, o el cast se puede hacer por el operador map con funcion de flecha sin function y return o como esta abajo
    //this.http.get<Cliente[]>(this.urlEndPoint);

    return this.http.get(this.urlEndPoint+'/page/'+page).pipe(
      tap(
        //response:any para poder manejar el json de paginator que trae content y la informacion de pagina
        (response:any) => {
          console.log('ClientesService: tap1');
          (response.content as Cliente[]).forEach(cliente =>{
            console.log(cliente.nombre);
          })
        }),
      map((response:any) => 
        {
         //let es un tipo de variable que se puede declarar en los metodos
         //let clientes = response as Cliente[];

         //el map se utiliza para modificar objetos o flujo de una lista o algo asi etc
         (response.content as Cliente[]).map(cliente =>{
           cliente.nombre = cliente.nombre.toUpperCase();
           
           let datePipe = new DatePipe('es');
           //para que el dia aparezca con nombres del dia se le agrega EEE E 3 o 4, mes: MMM, or fulldate, para español se coloca internacionalizacion en angular para buscar
           //cliente.createAt = datePipe.transform(cliente.createAt,'EEEE dd, MMMM  yyyy'); 
           //formatDate(cliente.createAt,'dd-MM-yyyy','en-US'); esta es una forma de cambiar el formato de fecha del cliente
           return cliente;
         });
         return response;
        }),tap(
        response => {
          console.log('ClientesService: tap2');
          (response.content as Cliente[]).forEach(cliente =>{
            console.log(cliente.nombre);
          })
      }),
    )
  }


create(cliente:Cliente) :Observable<Cliente>
{
  return this.http.post(this.urlEndPoint,cliente,{headers:this.agregarAutorizacion()}).pipe(
    //Este map lo utilizamos para convertir un objeto que existe en el json, en objeto cliente
    map((JSONObj:any)=>JSONObj.cliente as Cliente),
    catchError(e=>{
      if(this.isNoAutorizado(e))
      {
        return throwError(e);
      }
      if(e.status == 400)
      {
        return throwError(e);
      }
      console.error(e.error.mensaje);
      Swal.fire('Error al crear cliente',e.error.mensaje,'error');
      return throwError(e);
    })
  );
}

getCliente(id):Observable<Cliente>
{
  return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`,{'headers':this.agregarAutorizacion()}).pipe(
    catchError(e => {
      if(this.isNoAutorizado(e))
      {
        return throwError(e);
      }
      //error 400 viene de la validacion de campos desde backend
      if(e.status==400)
      {
        return throwError(e);
      }
      this.router.navigate(['/clientes'])
      console.error(e.error.mensaje);
      //e.error.mensaje es el que determinamos desde el backend
      Swal.fire('error al editar',e.error.mensaje,'error');
      return throwError(e);
    })
  );
}
//el observable de tipo any para que poder trabajar con json
update(cliente:Cliente):Observable<any>
{
  return this.http.put<any>(`${this.urlEndPoint}/${cliente.id}`,cliente,{headers:this.agregarAutorizacion()}).pipe(
    catchError(e=>{
      if(this.isNoAutorizado(e))
      {
        return throwError(e);
      }
      //error 400 viene de la validacion de campos desde backend
      if(e.status==400)
      {
        return throwError(e);
      }
      console.error(e.error.mensaje);
      Swal.fire('Error al actualizar cliente',e.error.mensaje,'error');
      return throwError(e);
    })
  );
}
delete(id:number):Observable<Cliente>
{
  return this.http.delete<Cliente>(`${this.urlEndPoint}/${id}`,{headers:this.agregarAutorizacion()}).pipe(
    catchError(e=>{
      if(this.isNoAutorizado(e))
      {
        return throwError(e);
      }
      console.error(e.error.mensaje);
      Swal.fire('Error al eliminar cliente',e.error.mensaje,'error');
      return throwError(e);
    })
  );
}

subirFoto(archivo:File, id):Observable<HttpEvent<{}>>
{
  let formData = new FormData();
  formData.append("archivo",archivo);
  formData.append("id",id);

  //para agregar la autorizacion es distinto 
  let httpHeaders = new HttpHeaders();
  let token = this.authService.obtenerToken();
  if(token != null)
  {
    //debe asignarse a httpheader por que append crea una nueva instancia a eso le dicen inmutable
   httpHeaders = httpHeaders.append('Authorization','Bearer '+token);
  }

  //para la el progreso de la imagen
  const req = new HttpRequest('POST',`${this.urlEndPoint}/upload`,formData,{
    reportProgress:true,
    //PARA LA SEGURIDAD PASARALE EL TOKEN
    headers:httpHeaders
  })
  return this.http.request(req).pipe(
    catchError(e =>{
      this.isNoAutorizado(e);
      return throwError(e);
    })
  );
}

private isNoAutorizado(e):boolean
{
  //401 es no autorizado, 403 es forgiben osea recurso no autorizado
  if(e.status== 401)
  {

    //preguntamos si el token expiro, si expiro entonces cerramos
    if(this.authService.estaAutenticado()){
      this.authService.logout();
    }

    this.router.navigate(['/login'])
    return true;
  }
  else if(e.status == 403)
  {
    Swal.fire("Acceso Denegado","que hace por aca ?",'error');
    this.router.navigate(['/clientes'])
    return true;
  }
  return false;
}

}
