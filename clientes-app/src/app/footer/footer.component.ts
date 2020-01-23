import { Component } from '@angular/core';

@Component({
    selector:'app-footer',
    templateUrl:'./footer.component.html',
    // se coloca en un array para que pueda tener varios stilos si uno asi lo quiere
    styleUrls:['./footer.component.css']
})
export class FooterComponent {
    public autor:any={nombre:'Mateo',apellido:'Henao'}
}
