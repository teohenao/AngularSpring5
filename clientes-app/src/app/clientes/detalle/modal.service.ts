import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  modal:boolean = false;
  //eventEmitter es el encargado de notificar y actualizar la lista al subir una imagen, el _ es necesario para private y abajo get
  private _notificarSubirArchivo = new EventEmitter<any>();

  constructor() { }

  get notificarSubirArchivo():EventEmitter<any>
  {
    return this._notificarSubirArchivo;
  }

  abrirModal(){
    this.modal = true;
  }

  cerrarModal()
  {
    this.modal = false;
  }
}
