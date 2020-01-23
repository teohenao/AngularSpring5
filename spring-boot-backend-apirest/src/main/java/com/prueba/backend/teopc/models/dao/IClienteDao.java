package com.prueba.backend.teopc.models.dao;
import org.springframework.data.repository.CrudRepository;
import com.prueba.backend.teopc.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente,Long>{

}
