package com.prueba.backend.teopc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootBackendApirestApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBackendApirestApplication.class, args);
	}

	@Autowired
	private BCryptPasswordEncoder contraseniaCodificada;
	
	//metodo para ejecutar algo antes de arrancar la aplicacion	,metodo para codificar contrasenias
	@Override
	public void run(String... args) throws Exception {
		String password ="12345";
		for (int i = 0; i < 4; i++) {
			String passwordBCypt = contraseniaCodificada.encode(password);
			System.out.println(passwordBCypt);
		}
	}

}
