package com.prueba.backend.teopc.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 2,max = 12)
	@Column(nullable = false)
	private String nombre;
	@NotEmpty(message = "no puede estar vacio")
	@Column(nullable = false)
	private String apellido;
	@NotEmpty(message = "no puede estar vacio")
	@Email(message = "debe ser un formato de email")
	@Column(nullable = false,unique = true)
	private String email;
	
	//se coloca not null para campos como date u otro objeto, mientras que en estring es notempty
	@Column(name = "create_at")
	@NotNull(message = "fecha campo no puede ser vacio")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	//Prepersist es el encargado de que antes de persistir el cliente se ejecute este metodo
//	@PrePersist
//	public void prePersist()
//	{
//		createAt = new Date();
//	}
//	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
