import { Component, OnInit } from '@angular/core';
import { Factura } from './models/factura';
import { ClienteService } from '../clientes/cliente.service';
import { ActivatedRoute } from '@angular/router';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { startWith, map, flatMap } from 'rxjs/operators';
import { FacturasService } from './services/facturas.service';
import { Producto } from './models/producto';
import { MatAutocompleteSelectedEvent } from '@angular/material';
import { ItemFactura } from './models/item-factura';

@Component({
  selector: 'app-facturas',
  templateUrl: './facturas.component.html'
})
export class FacturasComponent implements OnInit {

  titulo:string='nueva factura';
  factura:Factura = new Factura;
  autocompletecontrol = new FormControl();
  productosFiltrados: Observable<Producto[]>;

  constructor(private clienteService:ClienteService,private activateRoute:ActivatedRoute,private facturaService:FacturasService) { }

  ngOnInit() {
    this.activateRoute.paramMap.subscribe(params=>{
      let clienteId = +params.get('clienteId');//el nombre del parametro es el mismo que se coloca en el route
      this.clienteService.getCliente(clienteId).subscribe(cliente=>{
        this.factura.cliente = cliente;
      });
    });

    this.productosFiltrados = this.autocompletecontrol.valueChanges
    .pipe(
      // con tipe of se pregunta si es de cierto tipo,si es verdad return value, o si no el value nombre
      map(value => typeof value === 'string'? value:value.nombre),
      flatMap(value => value? this._filter(value):[])//se pregunta si value exite, y devuelve la lista, si no devuelve una lista vacia
    );
  }

  private _filter(value: string): Observable<Producto[]> {
    const filterValue = value.toLowerCase();

    return this.facturaService.filtrarProductos(filterValue);
  }


  mostrarNombre(producto?:Producto):string | undefined{
    return producto? producto.nombre :undefined;
  }

  seleccionarProducto(event:MatAutocompleteSelectedEvent):void{
    let productoSeleccionado = event.option.value as Producto;
    console.log(productoSeleccionado);


    if(this.existeItem(productoSeleccionado.id))
    {
      this.incrementaCantidad(productoSeleccionado.id)
    }else{
    let nuevoItem = new ItemFactura();
    nuevoItem.producto = productoSeleccionado;
    this.factura.items.push(nuevoItem);//agregamos el item a la factura
    }
    //reiniciamos el campo
    this.autocompletecontrol.setValue("");
    event.option.focus();
    event.option.deselect();
    
  }

  actualizarCantidad(id:number,event:any):void{
    let cantidad:number = event.target.value as number;

    this.factura.items = this.factura.items.map((item:ItemFactura)=>{
      if(id=== item.producto.id){
        item.cantidad = cantidad;
      }
      return item;
    });
  }

  existeItem(id:number):boolean{
    let existe = false;

    //el map, es para modificar, el for each es para iterar
    this.factura.items.forEach((item:ItemFactura)=>{
      if(id===item.producto.id)
      {
        existe = true;
      }
    })
    return existe;
  }

  incrementaCantidad(id:number):void{
    this.factura.items = this.factura.items.map((item:ItemFactura)=>{
      if(id=== item.producto.id){
        ++item.cantidad; //incrementamos en una unidad
      }
      return item;
    });

  }

}
