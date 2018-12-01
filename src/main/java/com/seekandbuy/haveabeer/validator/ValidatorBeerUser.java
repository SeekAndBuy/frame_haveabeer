package com.seekandbuy.haveabeer.validator;

import com.seekandbuy.haveabeer.domain.CandidateUser;

public class ValidatorBeerUser implements Validator<CandidateUser>{

	@Override
	public boolean validator(CandidateUser bUser) {
		
		if(bUser.getBeerCharacteristic() == null)
			return false;
		
		if(bUser.getName() == null)
			return false;
		
		return true;
	}
}
