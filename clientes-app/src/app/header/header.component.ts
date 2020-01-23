import { Component } from '@angular/core';

@Component({
    selector:'app-header',
    templateUrl:'./header.component.html'
})
export class HeaderComponent
{

    //cuando se utiliza {{}} en el html se llama interpolacion de string
    title:string = 'AppAngular-Spring';

}