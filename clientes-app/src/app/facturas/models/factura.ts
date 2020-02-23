import { ItemFactura } from './item-factura';
import { Cliente } from 'src/app/clientes/cliente';

export class Factura {
    id:number;
    descripcion:string;
    observacion:string;
    //relacion con items
    items:ItemFactura[]=[];
    cliente:Cliente;
    total:number;
    createAt:string;
}
