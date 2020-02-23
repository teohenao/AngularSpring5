package com.prueba.backend.teopc.models.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prueba.backend.teopc.models.dao.IClienteDao;
import com.prueba.backend.teopc.models.dao.IFacturaDao;
import com.prueba.backend.teopc.models.entity.Cliente;
import com.prueba.backend.teopc.models.entity.Factura;
import com.prueba.backend.teopc.models.entity.Region;

@Service
public class ClienteServiceImplement implements IClienteService {
	
	/**
	 * Autowired es para inyectar el Dao
	 */
	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IFacturaDao facturaDao;
	
	//en el transactional se coloca que desea, si solo lectura o escritura o todos etc
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() 
	{
		return (List<Cliente>) clienteDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		
		return clienteDao.findAll(pageable);
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

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegiones() {
		return clienteDao.findAllRegiones();
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		//si no existe return null
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Factura saveFactura(Factura factura) {
		return facturaDao.save(factura);
	}

	@Override
	@Transactional
	public void deleteFacturaById(Long id) {
		facturaDao.deleteById(id);
		
	}


}
