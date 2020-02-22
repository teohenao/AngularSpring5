package com.prueba.backend.teopc.models.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.backend.teopc.models.dao.IUsuarioDao;
import com.prueba.backend.teopc.models.entity.Usuario;

@Service
public class UsuarioService implements UserDetailsService,IUsuarioService {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null)
		{
			logger.error("error en el login el usuario no existe ");
			throw new UsernameNotFoundException("el usuario no esta regisyradi");
		}
		
		//usuario roles se convierte a autiruthies con stream, para stream y un map para recorrer e ir mapeando, y con el collector lo convertimo a la lista que esta esperando		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role -> { 
			return new SimpleGrantedAuthority(role.getNombre());
			//con el peek mostramos informacion del rol de cada uno por consola si queremos			
		}).peek(authority -> logger.info("Role"+authority.getAuthority()))
				.collect(Collectors.toList());
		//User es una libreria para trabajar con usuarios generales		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String username) {
		return usuarioDao.findByUsername(username);
	}

}
