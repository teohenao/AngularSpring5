package com.prueba.backend.teopc.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.backend.teopc.models.entity.Cliente;
import com.prueba.backend.teopc.models.entity.Region;
import com.prueba.backend.teopc.models.services.IClienteService;
import com.prueba.backend.teopc.models.services.IUploadFileService;

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
	
	@Autowired
	private IUploadFileService uploadService;
	

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
	 * paginando los registros 
	 * @param 
	 * @return
	 */
	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page)
	{
		//pagerequest con el of, queda estatico, y es el que nos ayuda con la paginacion el internamente hace el limit y offest y todo de la consulta, siendo page, el numero de pagina que se le pasa por parametro y el otro la cantidad de registros por pagina
		Pageable pageable = PageRequest.of(page,4); 
		return clienteService.findAll(pageable);
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
	//antes de Request le decimos que valide los campos con @Valid, es importante por que asi en entity diga sin esto no funciona, y result es para validar
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente,BindingResult result)
	{
		//cliente.setCreateAt(new Date()); esto lo hicimos mejor con Prepersist en entity cliente
		Cliente nuevoCliente = null;
		Map<String, Object> response  = new HashMap<>();
		//este try y catch maneja los campos unicos y nulos que tenemos en la entidad
		if(result.hasErrors())
		{
			
//		 //una lista que va a contener los errores de los campos, este es una manera para manejar los errores
//		 List<String> errors = new ArrayList<>();
//		 for(FieldError error :result.getFieldErrors())
//		 {
//			 errors.add("el campo: "+error.getField()+" "+error.getDefaultMessage());
//		 }
			
		 //esta es la otra manera para trabajar con esos errores, se convierten los errores a tipo lista y ese return de metodo flecha se utilza cuando tiene varias lineas, en este caso no afectaria quitarlo por que solo tiene una linea de codigo ese metodo flecha
		 List<String> errors = result.getFieldErrors()
				 .stream().map(err->{
					 return "El campo"+err.getField()+" "+err.getDefaultMessage();
				 }).collect(Collectors.toList());
		 
		 response.put("errors", errors);
		 return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			nuevoCliente = clienteService.save(cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Ocurrio un error en la bd");
			response.put("mensaje", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//el response es un archivo json con el mensaje y el cliente
		response.put("mensaje", "cliente creado con exito");
		//tambien se puede pasar en respuestas onjectos, el retorna todo esto como un json
		response.put("cliente", nuevoCliente);
		//fijese que se pasa el map para pasar la respuesta del objeto o mensaje en el servidor
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}

	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,BindingResult result, @PathVariable Long id)
	{
		Cliente clienteActual = clienteService.findById(id);
		Cliente clienteActualizado = null;		
		Map<String, Object> response  = new HashMap<>();
		if(result.hasErrors())
		{
			List<String> errors = result.getFieldErrors()
				 .stream().map(err->{
					 return "El campo"+err.getField()+" "+err.getDefaultMessage();
				 }).collect(Collectors.toList());
		 
		 response.put("errors", errors);
		 return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
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
			clienteActual.setRegion(cliente.getRegion());
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
	public ResponseEntity<?> delete(@PathVariable Long id)
	{
		Map<String, Object> response  = new HashMap<>();
		try {
			Cliente cliente = clienteService.findById(id);
			String nombreFotoAnterior = cliente.getFoto();
			uploadService.eliminar(nombreFotoAnterior);
			if(nombreFotoAnterior!=null && nombreFotoAnterior.length()>0)
	
			clienteService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Ocurrio un error en la bd");
			response.put("mensaje", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "eliminado con exito");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo,@RequestParam("id") Long id)
	{
		Map<String, Object> response  = new HashMap<>();
		Cliente cliente = clienteService.findById(id);
		if(!archivo.isEmpty())
		{
			String nombreArchivo = null;
			try {
				nombreArchivo = uploadService.copiar(archivo);
			}catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			String nombreFotoAnterior = cliente.getFoto();
			uploadService.eliminar(nombreFotoAnterior);
	
			cliente.setFoto(nombreArchivo);
			clienteService.save(cliente);
			response.put("cliente", cliente);
			response.put("mensaje", "has subido correctamente la imagen "+nombreArchivo);
		}
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED) ;
	}
	
	//metodo que permite ver la imagen, metodo hadler
	@GetMapping("/uploads/img/{nombreFoto}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto)
		{
		Resource recurso = null;
		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//con la cabecera se forza la descarga, lo hace el attachment, content disposition es para descargar e utilziar la imagen
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+recurso.getFilename()+"\"");
		
		return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);
	}
	
	@GetMapping("/clientes/regiones")
	public List<Region> listarRegiones()
	{
		return clienteService.findAllRegiones();
	}
	

}
