package com.prueba.backend.teopc.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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
	
	@Override
	@Bean("authenticationManager")
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	//este metodo es para desabilitar el manejo de sesion de estados por parte de spring
	//ya que se va a manejar desde el ResourceServerCOnfig
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//rutas publicas que cualquier usuario puede acceder este o no loggeado
		http.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	
	
	
}
