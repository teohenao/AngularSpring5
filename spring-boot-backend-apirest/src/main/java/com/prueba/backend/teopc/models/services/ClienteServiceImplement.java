package com.prueba.backend.teopc.models.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prueba.backend.teopc.models.dao.IClienteDao;
import com.prueba.backend.teopc.models.entity.Cliente;

@Service
public class ClienteServiceImplement implements IClienteService {
	
	/**
	 * Autowired es para inyectar el Dao
	 */
	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() 
	{
		return (List<Cliente>) clienteDao.findAll();
	}

}
