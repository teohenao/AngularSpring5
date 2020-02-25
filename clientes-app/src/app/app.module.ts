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
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
//import formularios de angular
import {FormsModule} from '@angular/forms'
import localeES from '@angular/common/locales/es'
import {registerLocaleData} from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatDatepickerModule,MatButtonModule,MatFormFieldModule,MatInputModule,MatRippleModule} from '@angular/material';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { DetalleComponent } from './clientes/detalle/detalle.component';
import { LoginComponent } from './usuarios/login.component';
import { AuthGuard } from './usuarios/guards/auth.guard';
import { RoleGuard } from './usuarios/guards/role.guard';
import { TokenInterceptor } from './usuarios/interceptors/token.interceptor';
import { AuthInterceptor } from './usuarios/interceptors/auth.interceptor';
import { DetalleFacturaComponent } from './facturas/detalle-factura.component';
import { FacturasComponent } from './facturas/facturas.component';

//internacionalizacion para el idioma de las fechas
registerLocaleData(localeES,'es');
const routes:Routes=[
  {path: '',redirectTo:'/clientes',pathMatch:'full'},
  {path:'directivas',component:DirectivaComponent},
  {path:'clientes',component:ClientesComponent},
  {path:'clientes/page/:page',component:ClientesComponent},
  {path:'clientes/form',component:FormComponent,canActivate:[AuthGuard,RoleGuard],data:{role:'ROLE_ADMIN'}},//canActive es un arreglo ya que se pueden tener varios guard si uno quiere
  {path:'clientes/form/:id',component:FormComponent,canActivate:[AuthGuard,RoleGuard],data:{role:'ROLE_ADMIN'}},
  {path:'login',component:LoginComponent},
  {path:'facturas/:id',component:DetalleFacturaComponent},
  {path:'facturas/form/:clienteId',component:FacturasComponent}
];

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    AppComponent,
    DirectivaComponent,
    ClientesComponent,
    FormComponent,
    PaginatorComponent,
    DetalleComponent,
    LoginComponent,
    DetalleFacturaComponent,
    FacturasComponent
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
  providers: [ClienteService,{provide:LOCALE_ID,useValue:'es'},
              {provide:HTTP_INTERCEPTORS,useClass:TokenInterceptor,multi:true},
              {provide:HTTP_INTERCEPTORS,useClass:AuthInterceptor,multi:true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
