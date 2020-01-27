import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';

import { AppComponent } from './app.component';
// Importar los componentes  en declarations para poder utilizarlos
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { DirectivaComponent } from './directiva/directiva.component';
import { ClientesComponent } from './clientes/clientes.component';
import { PaginatorComponent } from './paginator/paginator.component';
import { ClienteService } from './clientes/cliente.service';
import { FormComponent } from './clientes/form.component';
//import para las rutas
import { RouterModule, Routes } from '@angular/router';
//impoort para las peticiones http, conectar back y front
import { HttpClientModule } from '@angular/common/http';
//import formularios de angular
import {FormsModule} from '@angular/forms'
import localeES from '@angular/common/locales/es'
import {registerLocaleData} from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatDatepickerModule,MatButtonModule,MatFormFieldModule,MatInputModule,MatRippleModule} from '@angular/material';
import { MatMomentDateModule } from '@angular/material-moment-adapter';

//internacionalizacion para el idioma de las fechas
registerLocaleData(localeES,'es');
const routes:Routes=[
  {path: '',redirectTo:'/clientes',pathMatch:'full'},
  {path:'directivas',component:DirectivaComponent},
  {path:'clientes',component:ClientesComponent},
  {path:'clientes/page/:page',component:ClientesComponent},
  {path:'clientes/form',component:FormComponent},
  {path:'clientes/form/:id',component:FormComponent}
];

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    AppComponent,
    DirectivaComponent,
    ClientesComponent,
    FormComponent,
    PaginatorComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    //materias angular
    BrowserAnimationsModule,
    //material angular para datepicker
    MatMomentDateModule,
    MatDatepickerModule,MatButtonModule,MatFormFieldModule,MatInputModule,MatRippleModule
  ],
  providers: [ClienteService,{provide:LOCALE_ID,useValue:'es'}],
  bootstrap: [AppComponent]
})
export class AppModule { }
