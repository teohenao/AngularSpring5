package com.prueba.backend.teopc.models.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//import org.springframework.data.repository.CrudRepository; jpaRepository ya tiene todos estos incluidos y de paso el paginador
import com.prueba.backend.teopc.models.entity.Cliente;
import com.prueba.backend.teopc.models.entity.Region;

public interface IClienteDao extends JpaRepository<Cliente,Long>{
	
	//mapear metodo a consulta JPA, el nombre de la clase entity mas no de la tabla
	@Query("from Region")
	public List<Region> findAllRegiones();
	
}
