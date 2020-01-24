import { Injectable } from '@angular/core';
import { CLIENTES } from './clientes.json';
import { Cliente } from './cliente.js';
import { Observable, of } from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from 'rxjs/operators';


@Injectable()
export class ClienteService {

  private urlEndPoint:string = 'http://localhost:8080/api/clientes';

  private httpHeaders = new HttpHeaders({'Content-Type':'Application/json'});

  constructor(private http:HttpClient) { }



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
  return this.http.post<Cliente>(this.urlEndPoint,cliente,{headers:this.httpHeaders})
}

getCliente(id):Observable<Cliente>
{
  return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`);
}
update(cliente:Cliente):Observable<Cliente>
{
  return this.http.put<Cliente>(`${this.urlEndPoint}/${cliente.id}`,cliente,{headers:this.httpHeaders})
}
delete(id:number):Observable<Cliente>
{
  return this.http.delete<Cliente>(`${this.urlEndPoint}/${id}`,{headers:this.httpHeaders});
}
}
