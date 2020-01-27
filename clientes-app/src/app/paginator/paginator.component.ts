import { Component, OnInit, Input ,OnChanges} from '@angular/core';

@Component({
  selector: 'paginator-nav',
  templateUrl: './paginator.component.html'
})
export class PaginatorComponent implements OnInit,OnChanges {
  
  // con Input se coloca para injeccion de dependencias entre hijo y padre 
  @Input() paginador:any;

  //arreglo de numeros de la cantidad de paginas
  paginas:number[];

  //rangos del paginador
  desde:number;
  hasta:number;

  constructor() { }
  ngOnInit() {
  
  }

  ngOnChanges()
  {
      //rangos del paginador para cuando es mayor a 5
      this.desde = Math.min(Math.max(1,this.paginador.number-4),this.paginador.totalPages-5);
      this.hasta = Math.max(Math.min(this.paginador.totalPages,this.paginador.number+4),6);
      if(this.paginador.totalPages>5)
      {
        this.paginas = new Array(this.hasta -this.desde+1).fill(0).map((_valor,indice)=>
        indice+this.desde
      );
      }else{
      //total pages es un atributo que viene desde el paginador json , se le coloca un _ al valor que no usemos para que no chille
      this.paginas = new Array(this.paginador.totalPages).fill(0).map((_valor,indice)=>
        indice+1
      );
      }
  }

}
