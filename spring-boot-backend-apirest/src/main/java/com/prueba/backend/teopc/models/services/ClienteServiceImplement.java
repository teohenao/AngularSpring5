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
	
	//en el transactional se coloca que desea, si solo lectura o escritura o todos etc
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() 
	{
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		//orElse, si lo encuentra retorna un cliente de lo contrario null
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

}
