package com.prueba.backend.teopc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.backend.teopc.models.entity.Cliente;
import com.prueba.backend.teopc.models.services.IClienteService;

/**
 * el map es la direccion de la app con la que quiere iniciar y ya despues hacer los request
 * @author mateohenao
 */
//cross es el encargado de la comunicacion entre el back y el front se puede especificar en cross los request que le voy a permitir
@CrossOrigin(origins = {"http://localhost:42000"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;

	/**
	 * Se mapea clientes y antes de el va el api del request indicado
	 * @return
	 */
	@GetMapping("/clientes")
	public List<Cliente> index()
	{
		return clienteService.findAll();
	}
	
}
