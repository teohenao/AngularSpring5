import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html'
})
export class ClientesComponent implements OnInit {

  clientes:Cliente[]=[
    {id:1,nombre:'Andres',apellido:'Rodriguez',createAt:'2019-12-11',email:'andres@gmail.com'},
    {id:2,nombre:'Maria',apellido:'Perez',createAt:'2011-12-12',email:'Maria@gmail.com'},
    {id:3,nombre:'Andres3',apellido:'Rodriguez3',createAt:'2013-12-11',email:'andres3@gmail.com'}
  ];

  constructor() { }

  ngOnInit() {
  }

}
