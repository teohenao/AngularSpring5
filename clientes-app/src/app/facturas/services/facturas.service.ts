import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Factura } from '../models/factura';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FacturasService {

  private urlEndPoint:string = 'http://localhost:8080/api/facturas';

  constructor(private http:HttpClient) { }

  getFactura(id:number):Observable<Factura>
  {
    return this.http.get<Factura>(this.urlEndPoint+'/'+id);
  }
}
