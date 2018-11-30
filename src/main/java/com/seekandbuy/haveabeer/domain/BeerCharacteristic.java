package com.seekandbuy.haveabeer.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
public class BeerCharacteristic extends Characteristic{
	
	@JsonInclude(Include.NON_NULL)
	private String brand;
		
	@JsonInclude(Include.NON_NULL)
	private double price;
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String marca) {
		this.brand = marca;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}	
}
