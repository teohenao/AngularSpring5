import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html'
})
export class ClientesComponent implements OnInit {

  clientes:Cliente[];

  constructor(private clienteService:ClienteService)
  { 

  }

  ngOnInit() {
    this.clienteService.getClientes().subscribe(
      //funcion anonima, a la cual se le asigna clientes del servicio a los clientes de la clase
      clientes => this.clientes = clientes
    );
  }

}
