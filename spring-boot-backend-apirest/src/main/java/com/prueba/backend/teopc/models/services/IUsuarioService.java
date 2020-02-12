package com.prueba.backend.teopc.models.services;

import com.prueba.backend.teopc.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
	
}
