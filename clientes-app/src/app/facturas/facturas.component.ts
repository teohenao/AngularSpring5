import { Component, OnInit } from '@angular/core';
import { Factura } from './models/factura';
import { ClienteService } from '../clientes/cliente.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-facturas',
  templateUrl: './facturas.component.html'
})
export class FacturasComponent implements OnInit {

  titulo:string='nueva factura';
  factura:Factura = new Factura;

  constructor(private clienteService:ClienteService,private activateRoute:ActivatedRoute) { }

  ngOnInit() {
    this.activateRoute.paramMap.subscribe(params=>{
      let clienteId = +params.get('clienteId');//el nombre del parametro es el mismo que se coloca en el route
      this.clienteService.getCliente(clienteId).subscribe(cliente=>{
        this.factura.cliente = cliente;
      });
    });
  }

}
