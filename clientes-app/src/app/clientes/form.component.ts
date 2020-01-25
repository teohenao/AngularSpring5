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
  //atributo que tiene el arreglo de errores de campos en mensajes
  private errores:string[];

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
      cliente =>
      {this.route.navigate(['/clientes'])
      
      Swal.fire('Cliente guardado',`El cliente ${cliente.nombre} ha sido creado con exito`,'success')
      }
      //esta funcion despues de una de flecha, hace que esta funcione como un tipo de try catch
      ,err=>{
        //err es el json que trae desde el backend
        this.errores = err.error.errors as string[];
        console.error('codigo del error desde el backed '+err.status);
        console.error(err.error.errors);
      }
    );
  }

  update():void
  {
    this.clienteService.update(this.cliente)
    .subscribe(JSON=>{
      this.route.navigate(['/clientes'])
      //se lee el json de respuesta desde el backend
      Swal.fire('Cliente actualizado',`Cliente ${JSON.cliente.nombre} actualizado con exito`,'success')
    },err=>{
      //err es el json que trae desde el backend
      this.errores = err.error.errors as string[];
      console.error('codigo del error desde el backed '+err.status);
      console.error(err.error.errors);
    });
  }

}
