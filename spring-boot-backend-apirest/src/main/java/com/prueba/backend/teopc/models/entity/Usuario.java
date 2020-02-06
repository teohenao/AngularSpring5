package com.prueba.backend.teopc.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import ch.qos.logback.classic.db.names.ColumnName;

//el implements serializable es para trabajar con el objeto como json o peticiones http
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, length = 20)
	private String username;

	@Column(length = 60)
	private String password;

	private Boolean enabled;

	// carga perezosa con lazy y cascade para la eliminacion, la relacion de muchos a muchos, se puede hacer desde ambos lados o desde un solo lado como uno prefiera
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//se pueden modificar los nombres de las columnas foraneas y de la tabla intermedia asi:	
	@JoinTable(name = "usuarios_roles",
	joinColumns = @JoinColumn(name = "usuario_id"),
	inverseJoinColumns = @JoinColumn(name="rol_id"),
	//uniqueConstraint se utiliza para que un usuario no pueda repetir un mismo rol, caso tal de que un usuario sea admin no se le pueda volver a asignar admin
	uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","rol_id"})})
	private List<Rol> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	private static final long serialVersionUID = 1L;

}