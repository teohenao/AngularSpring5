package com.prueba.backend.teopc.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.prueba.backend.teopc.models.entity.Factura;
//Crud Repositoru recibe tipo de dato y el tipo de dato de la llave
public interface IFacturaDao extends CrudRepository<Factura, Long>{

}
