package com.prueba.backend.teopc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.backend.teopc.models.entity.Cliente;
import com.prueba.backend.teopc.models.services.IClienteService;

/**
 * el map es la direccion de la app con la que quiere iniciar y ya despues hacer los request
 * @author mateohenao
 */
//cross es el encargado de la comunicacion entre el back y el front se puede especificar en cross los request que le voy a permitir
@CrossOrigin(origins = {"http://localhost:4200"})
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
	
	/**
	 * PathVariable es para pasar el parametro por request
	 * @param id
	 * @return Cliente
	 */
	@GetMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.OK) //ese ok si no lo colocamos es creado por defecto, esto es redundancia, solo se coloca si es una respuesta diferente y si uno quiere
	public Cliente show(@PathVariable Long id)
	{
		return clienteService.findById(id); 
	}

	/**
	 * Requestbody por que el cliente viene del cuerpo de la peticion en formato json el se encarga de convertirlo en cliente
	 * @param cliente
	 * @return
	 */
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente create(@RequestBody Cliente cliente)
	{
		//cliente.setCreateAt(new Date()); esto lo hicimos mejor con Prepersist en entity cliente
		return clienteService.save(cliente);
	}
	
	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id)
	{
		Cliente clienteActual = clienteService.findById(id);
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setEmail(cliente.getEmail());
		return clienteService.save(clienteActual);
	}
	
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id)
	{
		clienteService.delete(id);
	}
	
}
