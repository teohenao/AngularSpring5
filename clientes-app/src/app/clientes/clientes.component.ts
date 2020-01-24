import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import Swal from 'sweetalert2';

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
