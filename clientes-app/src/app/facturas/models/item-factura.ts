import { Producto } from './producto';

export class ItemFactura {
    producto:Producto;
    cantidad:number = 1;
    importe:number;

    //se podria tener aca una funcion para calcular el importe
    public calcularImporte():number{
        return this.cantidad*this.producto.precio;
    }
}
