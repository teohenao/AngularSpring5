import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'paginator-nav',
  templateUrl: './paginator.component.html'
})
export class PaginatorComponent implements OnInit {
  
  // con Input se coloca para injeccion de dependencias entre hijo y padre 
  @Input() paginador:any;

  //arreglo de numeros de la cantidad de paginas
  paginas:number[];

  constructor() { }
  ngOnInit() {
    //total pages es un atributo que viene desde el paginador json , se le coloca un _ al valor que no usemos para que no chille
    this.paginas = new Array(this.paginador.totalPages).fill(0).map((_valor,indice)=>
      indice+1
    );
  }

}
