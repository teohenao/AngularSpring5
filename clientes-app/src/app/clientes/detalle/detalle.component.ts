import { Component, OnInit } from '@angular/core';
import { Cliente } from '../cliente';
import { ClienteService } from '../cliente.service';
import { ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2'

@Component({
  selector: 'detalle-cliente',
  templateUrl: './detalle.component.html',
  styleUrls: ['./detalle.component.css']
})
export class DetalleComponent implements OnInit {

  titulo:string = "Detalle del cliente";
  cliente:Cliente;
  private fotoSeleccionada:File;

  constructor(private clienteService:ClienteService,private activatedRoute:ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(paramId =>{
      let id:number = +paramId.get('id');
      if(id){
        this.clienteService.getCliente(id).subscribe(cliente=>{
          this.cliente = cliente;
        });
      }
    });
  }
  seleccionarFoto(event)
  {
    this.fotoSeleccionada = event.target.files[0];
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
      this.clienteService.subirFoto(this.fotoSeleccionada,this.cliente.id).subscribe(cliente=>{
        this.cliente = cliente;
        Swal.fire('La foto se ha subido correctamente','exito','success');
      })
    }
   
  }

}
