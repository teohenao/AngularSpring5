import { Injectable } from '@angular/core';
import { CLIENTES } from './clientes.json';
import { Cliente } from './cliente.js';

@Injectable()
export class ClienteService {

  constructor() { }

  getClientes():Cliente[]
  {
    return CLIENTES;
  }
}
