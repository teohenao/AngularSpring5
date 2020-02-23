package com.prueba.backend.teopc.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prueba.backend.teopc.models.entity.Cliente;
import com.prueba.backend.teopc.models.entity.Factura;
import com.prueba.backend.teopc.models.entity.Region;

//los services manejan y administran los diferentes accesos a los daos
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
	
	public List<Region> findAllRegiones();
	
	public Factura findFacturaById(Long id);
	
	public Factura saveFactura(Factura factura);
	
	public void deleteFacturaById(Long id);

}
