import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import Swal from 'sweetalert2';
import { ClienteService } from './cliente.service';
import {Router,ActivatedRoute} from '@angular/router'

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {

  private cliente:Cliente = new Cliente();
  private titulo:string = 'Crear Cliente';

  constructor(private clienteService:ClienteService,private route:Router,private activateRoute:ActivatedRoute) { }

  ngOnInit() {
    //para cargar el cliente seleccionado
    this.cargarCliente();
  }

  cargarCliente():void
  {
this.activateRoute.params.subscribe(params=>
  {let id = params['id']
  if(id)
    {
      this.clienteService.getCliente(id).subscribe(
        (cliente)=>this.cliente = cliente
      );
    }
  });
  }

  public create():void
  {
    //Metodo crear, y en su respuesta redirecciona a clientes de nuevo
    this.clienteService.create(this.cliente).subscribe(
      clienteResponse =>
      {this.route.navigate(['/clientes'])
      Swal.fire('Cliente guardado',`Cliente ${clienteResponse.nombre} creado con exito`,'success')}
    );
  }

  update():void
  {
    this.clienteService.update(this.cliente)
    .subscribe(clienteResponse=>{
      this.route.navigate(['/clientes'])
      Swal.fire('Cliente actualizado',`Cliente ${clienteResponse.nombre} actualizado con exito`,'success')
    });
  }

}
