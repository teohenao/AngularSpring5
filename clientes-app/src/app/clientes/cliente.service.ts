import { Injectable } from '@angular/core';
import { CLIENTES } from './clientes.json';
import { Cliente } from './cliente.js';
import { Observable, of,throwError } from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map,catchError} from 'rxjs/operators';
import Swal from 'sweetalert2';
import {Router} from '@angular/router'


@Injectable()
export class ClienteService {

  private urlEndPoint:string = 'http://localhost:8080/api/clientes';

  private httpHeaders = new HttpHeaders({'Content-Type':'Application/json'});

  constructor(private http:HttpClient,private router:Router) { }



  //observable y observadores, se subscriben a este cambio, es el patron de diseño observador
  //es para mantener atento a los cambios en el servidor y mantiene avisando a los cambios
  getClientes():Observable<Cliente[]>
  {
    //of para observables reactivos
    //return of(CLIENTES);
    
    //<CLienre[]> es un casteo, o el cast se puede hacer por el operador map con funcion de flecha sin function y return o como esta abajo
    //this.http.get<Cliente[]>(this.urlEndPoint);

    return this.http.get(this.urlEndPoint).pipe(
      map(response => response as Cliente[])
    );
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
}
