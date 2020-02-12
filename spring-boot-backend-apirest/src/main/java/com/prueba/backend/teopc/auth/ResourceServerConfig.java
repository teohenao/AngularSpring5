package com.prueba.backend.teopc.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	//METODO ENCARGADO DE LA SEGURIDA DE ROLES 	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//el orden es de lo mas especifico a lo mas generico de arriba hacia abajo en permisos		
		//rutas publicas que cualquier usuario puede acceder este o no loggeado
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/clientes","/api/clientes/page/**","/api/uploads/img/**").permitAll()

// esto se cambio EN EL CONTROLADOR POR LA ANOTACION SECURE, cualquiera de las dos funciona	
//		//permite obtener a usuarios y admin
//		.antMatchers(HttpMethod.GET,"/api/clientes/{id}").hasAnyRole("ROL_USUARIO","ROL_ADMIN")
//		.antMatchers(HttpMethod.POST,"/api/clientes/upload").hasAnyRole("ROL_USUARIO","ROL_ADMIN")
//		//permite post a solo admin
//		.antMatchers(HttpMethod.POST,"/api/clientes").hasRole("ROL_ADMIN") 
//		//RUTA GENERICA, despues de lo que sigue de una ruta, va a ser de un rol, como un limite
//		.antMatchers("/api/clientes/**").hasRole("ROL_ADMIN")
//		//estas se colocan siempre al final para todas las reglas, significa que las rutas que no se colocaron aca necesitan tener autentificacion y no depende de rol
		
		
		.anyRequest().authenticated();
	}

	
	
}
