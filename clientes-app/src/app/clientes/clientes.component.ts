import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import Swal from 'sweetalert2';
import {map,catchError,tap} from 'rxjs/operators';

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
    let page = 0;
    this.clienteService.getClientes(page).pipe(
    // el tap en si funciona para trabajar con los datos que nos estan mandando, este operador no retorna nada
    tap(resJson =>{
      console.log('ClienteComponent: tap3');
      (resJson.content as Cliente[]).forEach(cliente =>{
        console.log(cliente.nombre);
      });
    })
    //el suvscribe es un metodo que nos permite subscribirnos para un observable
    ).subscribe(
      responseJson => this.clientes = responseJson.content as Cliente[]
      //funcion anonima, a la cual se le asigna clientes del servicio a los clientes de la clase
      //clientes => this.clientes = clientes, se puede aca o en el tap como queramos, se necesita el subscribe por que si no no puede trabajar el observable 
    );
  }

  delete(cliente:Cliente):void
  {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })
    
    swalWithBootstrapButtons.fire({
      title: 'Esta Seguro?',
      text: `Seguro que desea eliminar el cliente ${cliente.nombre} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'SI, eliminar!',
      cancelButtonText: 'NO, cancelar!',
      reverseButtons: true
    }).then((result) => {
      if (result.value) {
        this.clienteService.delete(cliente.id).subscribe(
          response =>{
            //esta linea es para vilver a filtrar la lista y que quite el cliente eliminado, osea
            //mostrar todos diferentes al eliminado, ya que si se pudo eliminar 
            this.clientes = this.clientes.filter(cli => cli !== cliente)
            swalWithBootstrapButtons.fire(
              'Eliminado!',
              'El cliente ha sido eliminado con exito.',
              'success'
            )
          }
        );
      } else if (
        result.dismiss === Swal.DismissReason.cancel
      ) {
        swalWithBootstrapButtons.fire(
          'Cancelo la eliminacion',
          'El cliente aun existe :)',
          'error'
        )
      }
    })
  }


}
