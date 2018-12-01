package com.seekandbuy.haveabeer.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
public class JobCharacteristic extends Characteristic{
	
	@JsonInclude(Include.NON_NULL)
	private String escolaridade;
		
	@JsonInclude(Include.NON_NULL)
	private double salario;
	
	@JsonInclude(Include.NON_NULL)
	private  String tituloDaVaga;
	
	@JsonInclude(Include.NON_NULL)
	private  String area;
	
	@JsonInclude(Include.NON_NULL)
	private  String idioma;
	
	
	
	public String getTituloDaVaga() {
		return tituloDaVaga;
	}

	public void setTituloDaVaga(String tituloDaVaga) {
		this.tituloDaVaga = tituloDaVaga;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}	
}
