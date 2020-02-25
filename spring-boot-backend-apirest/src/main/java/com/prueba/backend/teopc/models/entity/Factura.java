package com.prueba.backend.teopc.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descripcion;
	private String observacion;
	
	@Column(name = "create_At")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	//muchas facturas corresponden a un cliente
	@ManyToOne(fetch = FetchType.LAZY) //allow setters de abajo es para el error en editar, cuando se tiene la relacion
	@JsonIgnoreProperties(value={"facturas","hibernateLazyInitializer","handler"},allowSetters = true)//para que el json no entre en bug infinito
	private Cliente cliente;
	
	//relacion unidimensional, esto quiere decir que no es necesario que la relacion este en items ya que no tiene sentido en la logica,
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	//como es unidimensional, y en la otra tabla no se crea el campo, es necesario crearlo desde aca
	@JoinColumn(name = "factura_id")//esta llave foranea se crea en factura.item
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private List<ItemFactura> items;
	
	
	public Factura()
	{
		items = new ArrayList<>();
	}
	
	//metodo que se ejecuta automaticamente
	@PrePersist
	public void preGuardar()
	{
		this.createAt = new Date();
	}
	
	//metodo que calcula el total de la factura
	public Double getTotal()
	{
		Double total = 0.00;
		for(ItemFactura item:items)
		{
			total += item.getImporte();
		}
		return total;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
