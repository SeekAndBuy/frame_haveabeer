package com.seekandbuy.haveabeer.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@MappedSuperclass
public abstract class Characteristic 
{
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@JsonInclude(Include.NON_NULL)
	private Long Id;
	
	public Long getId() {
		return Id;
	}
 	public void setId(Long id) {
		Id = id;
	}
}
