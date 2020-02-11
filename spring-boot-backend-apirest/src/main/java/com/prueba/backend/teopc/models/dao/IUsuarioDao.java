package com.prueba.backend.teopc.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.prueba.backend.teopc.models.entity.Usuario;


//estas interfaces son necesarias para utilizar el crud que tenemos en la libreria, existen varias como jpa repository y mas
public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
	//la nombrada del metodo es importante si queremos pasar mas parametros podemos colocar findByUserNameAndEmailOrEjemplo, y asi y pasar los parametros que queramos asi	
	public Usuario findByUsername(String username);
	
	//ejemplo con query 1 es el parametro uno o los que uno quiera pasar 1, 2, 3	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsername2(String username);
	

}
