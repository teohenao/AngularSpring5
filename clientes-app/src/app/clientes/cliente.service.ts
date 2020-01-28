import { Injectable } from '@angular/core';
import { CLIENTES } from './clientes.json';
import { Cliente } from './cliente.js';
import { Observable, of,throwError } from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map,catchError,tap} from 'rxjs/operators';
import Swal from 'sweetalert2';
import {DatePipe} from '@angular/common';
import {Router} from '@angular/router'


@Injectable()
export class ClienteService {

  private urlEndPoint:string = 'http://localhost:8080/api/clientes';

  private httpHeaders = new HttpHeaders({'Content-Type':'Application/json'});

  constructor(private http:HttpClient,private router:Router) { }



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
  return this.http.post(this.urlEndPoint,cliente,{headers:this.httpHeaders}).pipe(
    //Este map lo utilizamos para convertir un objeto que existe en el json, en objeto cliente
    map((JSONObj:any)=>JSONObj.cliente as Cliente),
    catchError(e=>{
      console.error(e.error.mensaje);
      Swal.fire('Error al crear cliente',e.error.mensaje,'error');
      return throwError(e);
    })
  );
}

getCliente(id):Observable<Cliente>
{
  return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`).pipe(
    catchError(e => {
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
  return this.http.put<any>(`${this.urlEndPoint}/${cliente.id}`,cliente,{headers:this.httpHeaders}).pipe(
    catchError(e=>{
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
  return this.http.delete<Cliente>(`${this.urlEndPoint}/${id}`,{headers:this.httpHeaders}).pipe(
    catchError(e=>{
      console.error(e.error.mensaje);
      Swal.fire('Error al eliminar cliente',e.error.mensaje,'error');
      return throwError(e);
    })
  );
}

subirFoto(archivo:File, id):Observable<Cliente>
{
  let formData = new FormData();
  formData.append("archivo",archivo);
  formData.append("id",id);
  return this.http.post(`${this.urlEndPoint}/upload/`,formData).pipe(
    map((response:any)=>response.cliente as Cliente),
    catchError(e=>{
      console.error(e.error.mensaje);
      Swal.fire('Error al eliminar cliente',e.error.mensaje,'error');
      return throwError(e);
    })
  );
}
}
