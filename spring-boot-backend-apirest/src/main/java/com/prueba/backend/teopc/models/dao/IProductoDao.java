package com.prueba.backend.teopc.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.prueba.backend.teopc.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto,Long>{
	
	@Query("select p from Producto p where p.nombre like %?1%") // con el 1 hacemos referencia al primer parametro del metodo
	public List<Producto> findByNombre(String term);
	
	//en la libreria de spring querys, existen palabras reservadas que se encargan de hacer este tipo de consultas; ejemplo
	public List<Producto> findByNombreContainingIgnoreCase(String term);
	
	public List<Producto> findByNombreStartingWithIgnoreCase(String term);

}
