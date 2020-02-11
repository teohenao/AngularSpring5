package com.prueba.backend.teopc.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//esta clase es implementada pra trabajar con el servicio de usuario y los jwt 
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	//es userDetail ya que usuarioService utiliza lo de userDetail entonces es mas concreto 	
	@Autowired
	private UserDetailsService usuarioService;

	
	//con este bean se puede utlizar lo que trae este metodo en otra clase con autowhired	
	@Bean
	public BCryptPasswordEncoder decodificandoContrasenia() {
		return new BCryptPasswordEncoder();
	}
	//en el menu de click derecho sobrescribimos en source este metodo	
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.usuarioService).passwordEncoder(decodificandoContrasenia());
	}
	
	
	
	
}
