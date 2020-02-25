package com.prueba.backend.teopc.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	//METODO ENCARGADO DE LA SEGURIDA DE ROLES 	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//el orden es de lo mas especifico a lo mas generico de arriba hacia abajo en permisos		
		//rutas publicas que cualquier usuario puede acceder este o no loggeado
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/clientes","/api/clientes/page/**","/api/uploads/img/**","/images/**").permitAll()
/* esto se cambio EN EL CONTROLADOR POR LA ANOTACION SECURE, cualquiera de las dos funciona	
//		//permite obtener a usuarios y admin
		.antMatchers(HttpMethod.GET,"/api/clientes/{id}").hasAnyRole("USUARIO","ADMIN")
		.antMatchers(HttpMethod.POST,"/api/clientes/upload").hasAnyRole("USUARIO","ADMIN")
		//permite post a solo admin
		.antMatchers(HttpMethod.POST,"/api/clientes").hasRole("ADMIN") 
		//RUTA GENERICA, despues de lo que sigue de una ruta, va a ser de un rol, como un limite
		.antMatchers("/api/clientes/**").hasRole("ADMIN")
		//estas se colocan siempre al final para todas las reglas, significa que las rutas que no se colocaron aca necesitan tener autentificacion y no depende de rol
		*/.anyRequest().authenticated()
		//necesario para el cors
		.and().cors().configurationSource(corsConfigurationSource());
	}

	//Spring securiry cors, solucionando, se puede encontrar en la libreri de spring, cors es la segudirad de consumir desde un dominio a otro
	@Bean
	public CorsConfigurationSource corsConfigurationSource()
	{
		CorsConfiguration config = new CorsConfiguration();
		//permitir el dominio del clente
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));// CON * SE PUEDE DECIR QUE PARA TODOS
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));
		
		//la version que no es reactiva ojo
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	//filtro 
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter()
	{
		 FilterRegistrationBean<CorsFilter> bean = new  FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		 bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		 return bean;
	}
	
	
}
