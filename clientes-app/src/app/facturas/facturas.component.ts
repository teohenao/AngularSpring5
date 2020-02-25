import { Component, OnInit } from '@angular/core';
import { Factura } from './models/factura';
import { ClienteService } from '../clientes/cliente.service';
import { ActivatedRoute } from '@angular/router';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';

@Component({
  selector: 'app-facturas',
  templateUrl: './facturas.component.html'
})
export class FacturasComponent implements OnInit {

  titulo:string='nueva factura';
  factura:Factura = new Factura;
  autocompletecontrol = new FormControl();
  productos: string[] = ['Mesa', 'Table', 'Three'];
  productosFiltrados: Observable<string[]>;

  constructor(private clienteService:ClienteService,private activateRoute:ActivatedRoute) { }

  ngOnInit() {
    this.activateRoute.paramMap.subscribe(params=>{
      let clienteId = +params.get('clienteId');//el nombre del parametro es el mismo que se coloca en el route
      this.clienteService.getCliente(clienteId).subscribe(cliente=>{
        this.factura.cliente = cliente;
      });
    });

    this.productosFiltrados = this.autocompletecontrol.valueChanges
    .pipe(
      startWith(''),
      map(value => this._filter(value))
    );
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.productos.filter(option => option.toLowerCase().includes(filterValue));
  }

}
