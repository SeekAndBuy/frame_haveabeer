package com.seekandbuy.haveabeer.validator;

import com.seekandbuy.haveabeer.domain.Beer;

public class ValidatorBeer implements Validator<Beer>{

	@Override
	public boolean validator(Beer beer) {
		
		if(beer.getBeerCharacteristic() == null)
			return false;
		if(beer.getDate() == null)
			return false;
		if(beer.getUser() == null)
			return false;
		
		return true;
	}

}
