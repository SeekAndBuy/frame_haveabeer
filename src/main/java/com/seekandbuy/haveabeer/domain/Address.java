package com.seekandbuy.haveabeer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonInclude(Include.NON_NULL)
	private long id;
	
	@JsonInclude(Include.NON_NULL)
	private String city;
	
	@JsonInclude(Include.NON_NULL)
	private String state;
	
	@JsonInclude(Include.NON_NULL)
	private String street;
	
	@JsonInclude(Include.NON_NULL)
	private String number;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String cidade) {
		this.city = cidade;
	}
	public String getState() {
		return state;
	}
	public void setState(String estado) {
		this.state = estado;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String rua) {
		this.street = rua;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String numero) {
		this.number = numero;
	}	
}

