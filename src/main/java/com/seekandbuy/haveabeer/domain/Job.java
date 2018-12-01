package com.seekandbuy.haveabeer.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
public class Job extends Product {
	
	@JsonInclude(Include.NON_NULL)
	@Cascade(CascadeType.PERSIST)
	@ManyToOne
	private JobCharacteristic beerCharacteristic;
	
	public JobCharacteristic getJobCharacteristic() {
		return beerCharacteristic;
	}
	
	public void setJobCharacteristic(JobCharacteristic jobCharacteristic) {
		this.beerCharacteristic = jobCharacteristic;
	}
	
	@JsonInclude(Include.NON_NULL)
	@Cascade(CascadeType.PERSIST)
	@ManyToOne
	private CandidateUser user;
	
	public CandidateUser getUser() {
		return user;
	}

	public void setUser(CandidateUser user) {
		this.user = user;
	}
}
