import { Component, OnInit, Input } from '@angular/core';
import { Cliente } from '../cliente';
import { ClienteService } from '../cliente.service';
import Swal from 'sweetalert2'
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
import { AuthService } from 'src/app/usuarios/auth.service';
import { FacturasService } from 'src/app/facturas/services/facturas.service';
import { Factura } from 'src/app/facturas/models/factura';

@Component({
  selector: 'detalle-cliente',
  templateUrl: './detalle.component.html',
  styleUrls: ['./detalle.component.css']
})
export class DetalleComponent implements OnInit {

  titulo:string = "Detalle del cliente";
  
  @Input()
  cliente:Cliente;
  public fotoSeleccionada:File;
  progreso:number = 0;

  constructor(private clienteService:ClienteService,public modalService:ModalService,public authService:AuthService
    ,private facturaService:FacturasService) { }

  ngOnInit() {
    //se cambio en el modal
    // this.activatedRoute.paramMap.subscribe(paramId =>{
    //   let id:number = +paramId.get('id');
    //   if(id){
    //     this.clienteService.getCliente(id).subscribe(cliente=>{
    //       this.cliente = cliente;
    //     });
    //   }
    // });
  }
  seleccionarFoto(event)
  {
    this.fotoSeleccionada = event.target.files[0];
    this.progreso = 0;
    console.log(this.fotoSeleccionada);
    //indexOf busca en la cadena algo relacionado con un image, si lo encuentra retorna la posicion en que se encuentra, la primera ocurrencia
    if(this.fotoSeleccionada.type.indexOf('image')<0)
    {
      Swal.fire('Error','debe seleccionar una imagen valida','error');
      this.fotoSeleccionada = null;
    }
  }

  subirFoto()
  {
    if(!this.fotoSeleccionada)
    {
      Swal.fire('Error','debe seleccionar una foto','error');
    }else{
      this.clienteService.subirFoto(this.fotoSeleccionada,this.cliente.id).subscribe(event=>{
        //calculamos el progreso de la tranferencia de subida
        if(event.type === HttpEventType.UploadProgress)
        {
          //calcular el porcentaje
          this.progreso = Math.round((event.loaded/event.total)*100);
        }else if(event.type === HttpEventType.Response){
          let response:any = event.body;
          this.cliente = response.cliente as Cliente;

          // actualizar la lista al subir una foto por medio del eventEmmit de emitit
          this.modalService.notificarSubirArchivo.emit(this.cliente);

          Swal.fire('La foto se ha subido correctamente',response.mensaje,'success');
        }
      })
    }
  }

  cerrarModal()
  {
    this.modalService.cerrarModal();
    this.fotoSeleccionada = null;
    this.progreso = 0;
  }

  delete(factura:Factura):void
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
      text: `Seguro que desea eliminar la factura ${factura.descripcion} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'SI, eliminar!',
      cancelButtonText: 'NO, cancelar!',
      reverseButtons: true
    }).then((result) => {
      if (result.value) {
        this.facturaService.detele(factura.id).subscribe(
          response =>{
            //esta linea es para vilver a filtrar la lista y que quite el cliente eliminado, osea
            //mostrar todos diferentes al eliminado, ya que si se pudo eliminar 
            this.cliente.facturas = this.cliente.facturas.filter(fac => fac !== factura)
            swalWithBootstrapButtons.fire(
              'Eliminada!',
              'Factura ha sido eliminado con exito.',
              'success'
            )
          }
        );
      } else if (
        result.dismiss === Swal.DismissReason.cancel
      ) {
        swalWithBootstrapButtons.fire(
          'Cancelo la eliminacion',
          'Factura aun existe :)',
          'error'
        )
      }
    })
  }
}
