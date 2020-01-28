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
  }

  subirFoto()
  {
    this.clienteService.subirFoto(this.fotoSeleccionada,this.cliente.id).subscribe(cliente=>{
      this.cliente = cliente;
      Swal.fire('La foto se ha subido correctamente','exito','success');
    })
  }

}
