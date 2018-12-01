package com.seekandbuy.haveabeer.validator;

import com.seekandbuy.haveabeer.domain.Job;

public class ValidatorBeer implements Validator<Job>{

	@Override
	public boolean validator(Job beer) {
		
		if(beer.getJobCharacteristic() == null)
			return false;
		if(beer.getDate() == null)
			return false;
		if(beer.getUser() == null)
			return false;
		
		return true;
	}

}
