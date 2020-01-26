package com.prueba.backend.teopc.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prueba.backend.teopc.models.entity.Cliente;

public interface IClienteService {
	
	//obtener todos los clientes
	public List<Cliente> findAll();
	
	//obtener rango de los clientes
	public Page<Cliente> findAll(Pageable pageable);
	
	//obtener un cliente por medio del id
	public Cliente findById(Long id);
	
	//guardar un cliente
	public Cliente save(Cliente cliente);
	
	//eliminar un cliente
	public void delete(Long id);

}
