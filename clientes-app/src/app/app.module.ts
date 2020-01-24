import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
// Importar los componentes  en declarations para poder utilizarlos
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { DirectivaComponent } from './directiva/directiva.component';
import { ClientesComponent } from './clientes/clientes.component';
import { ClienteService } from './clientes/cliente.service';
//import para las rutas
import { RouterModule, Routes } from '@angular/router';
//impoort para las peticiones http, conectar back y front
import { HttpClientModule } from '@angular/common/http';

const routes:Routes=[
  {path: '',redirectTo:'/clientes',pathMatch:'full'},
  {path:'directivas',component:DirectivaComponent},
  {path:'clientes',component:ClientesComponent}
];

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    AppComponent,
    DirectivaComponent,
    ClientesComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],
  providers: [ClienteService],
  bootstrap: [AppComponent]
})
export class AppModule { }
