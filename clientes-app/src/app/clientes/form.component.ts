import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import swal from 'sweetalert2';
import { ClienteService } from './cliente.service';
import {Router} from '@angular/router'

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {

  private cliente:Cliente = new Cliente();
  private titulo:string = 'Crear Cliente';

  constructor(private clienteService:ClienteService,private route:Router) { }

  ngOnInit() {
  }

  public create():void
  {
    //Metodo crear, y en su respuesta redirecciona a clientes de nuevo
    this.clienteService.create(this.cliente).subscribe(
      response => this.route.navigate(['/clientes'])
    );
  }

}
