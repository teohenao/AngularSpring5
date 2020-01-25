package com.prueba.backend.teopc.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> show(@PathVariable Long id)// ? es tipo de dato generic, que puede ser cualquiera, responseEntity se coloca por que puede devolver un error si el cliente no existe /id
	{
		Cliente cliente = null;
		//map es encargado de la respuesta al servidor, por ejemplo en caso de fallos
		Map<String, Object> response  = new HashMap<>();
		//try catch para manejar errores de conexion o por parte del servidor
		try {
			//cliente encontrado por id
			cliente = clienteService.findById(id); 
		} catch (DataAccessException e) {
			response.put("mensaje", "Ocurrio un error en la bd");
			response.put("mensaje", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(cliente == null)
		{
			response.put("mensaje", "El cliente Id:".concat(id.toString()).concat(" No existe"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente,HttpStatus.OK); 
	}

	/**
	 * Requestbody por que el cliente viene del cuerpo de la peticion en formato json el se encarga de convertirlo en cliente
	 * @param cliente
	 * @return
	 */
	@PostMapping("/clientes")
	// esto se quita por que varia entre si es o no creado  por eso el return entity -> @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody Cliente cliente)
	{
		//cliente.setCreateAt(new Date()); esto lo hicimos mejor con Prepersist en entity cliente
		Cliente nuevoCliente = null;
		Map<String, Object> response  = new HashMap<>();
		//este try y catch maneja los campos unicos y nulos que tenemos en la entidad
		try {
			nuevoCliente = clienteService.save(cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Ocurrio un error en la bd");
			response.put("mensaje", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//el response es un archivo json con el mensaje y el cliente
		response.put("mensaje", "cliente creado con exito");
		//tambien se puede pasar en respuestas onjectos
		response.put("cliente", nuevoCliente);
		//fijese que se pasa el map para pasar la respuesta del objeto o mensaje en el servidor
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}

	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id)
	{
		Cliente clienteActual = clienteService.findById(id);
		Cliente clienteActualizado = null;		
		Map<String, Object> response  = new HashMap<>();
		if(clienteActual == null)
		{
			response.put("mensaje", "El cliente Id:".concat(id.toString()).concat(" No existe"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setCreateAt(cliente.getCreateAt());
			clienteActualizado = clienteService.save(clienteActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Ocurrio un error en la bd");
			response.put("mensaje", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	
		response.put("mensaje", "cliente actualizado con exito");
		response.put("cliente", clienteActualizado);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED) ;
	}

	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id)
	{
		clienteService.delete(id);
	}

}
