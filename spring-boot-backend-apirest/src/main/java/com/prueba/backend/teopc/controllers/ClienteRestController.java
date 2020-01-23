package com.prueba.backend.teopc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.backend.teopc.models.entity.Cliente;
import com.prueba.backend.teopc.models.services.IClienteService;

/**
 * el map es la direccion de la app con la que quiere iniciar y ya despues hacer los request
 * @author mateohenao
 */
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
