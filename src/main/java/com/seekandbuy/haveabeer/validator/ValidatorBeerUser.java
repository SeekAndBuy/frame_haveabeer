package com.seekandbuy.haveabeer.validator;

import com.seekandbuy.haveabeer.domain.BeerUser;

public class ValidatorBeerUser implements Validator<BeerUser>{

	@Override
	public boolean validator(BeerUser bUser) {
		
		if(bUser.getBeerCharacteristic() == null)
			return false;
		
		if(bUser.getName() == null)
			return false;
		
		return true;
	}
}
