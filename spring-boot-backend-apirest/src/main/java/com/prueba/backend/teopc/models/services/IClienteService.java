package com.prueba.backend.teopc.models.services;

import java.util.List;

import com.prueba.backend.teopc.models.entity.Cliente;

public interface IClienteService {
	
	public List<Cliente> findAll();

}
