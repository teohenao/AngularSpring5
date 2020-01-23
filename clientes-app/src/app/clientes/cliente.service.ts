import { Injectable } from '@angular/core';
import { CLIENTES } from './clientes.json';
import { Cliente } from './cliente.js';

import { Observable, of } from 'rxjs';


@Injectable()
export class ClienteService {

  constructor() { }

  //observable y observadores, se subscriben a este cambio, es el patron de diseño observador
  //es para mantener atento a los cambios en el servidor y mantiene avisando a los cambios
  getClientes():Observable<Cliente[]>
  {
    //of para observables reactivos
    return of(CLIENTES);
  }
}
