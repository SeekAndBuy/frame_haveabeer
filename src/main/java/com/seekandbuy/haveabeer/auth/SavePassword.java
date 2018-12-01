package com.seekandbuy.haveabeer.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.seekandbuy.haveabeer.dao.CandidateUserDao;
import com.seekandbuy.haveabeer.domain.User;
import com.seekandbuy.haveabeer.exceptions.ProductNotFoundException;
import com.seekandbuy.haveabeer.exceptions.TokenNotFoundException;

public class SavePassword {
	private CandidateUserDao userDao;
	private Map<String, Object> codification;
	
	public SavePassword(){
		codification = new HashMap<String, Object>();
	}
	
	public String tokenizerPassword(String password) {
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		boolean validToken = false;
		
		String token = null;
		while(!validToken) {
			token = myRandom.substring(0,20); //gera uma chave de token
			if(!codification.containsKey(token)) { //se essa chave nao estiver na hash, adiciona ligada o password informado
				validToken = true;
			}
		}
		codification.put(password, token);
		return token;
	}
	
	public String findTokenByPassword(String password){
		
		String token = (String) codification.get(password);
		if(token == null) {
			throw new TokenNotFoundException("Token can not be found. Please, put the correct password!");
		}
		return token;
		
	}
}
