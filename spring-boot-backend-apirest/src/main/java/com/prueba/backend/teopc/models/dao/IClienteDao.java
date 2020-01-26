package com.prueba.backend.teopc.models.dao;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository; jpaRepository ya tiene todos estos incluidos y de paso el paginador
import com.prueba.backend.teopc.models.entity.Cliente;

public interface IClienteDao extends JpaRepository<Cliente,Long>{

}
