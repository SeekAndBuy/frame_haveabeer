package com.seekandbuy.haveabeer.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.seekandbuy.haveabeer.dao.UserDao;
import com.seekandbuy.haveabeer.domain.BeerUser;
import com.seekandbuy.haveabeer.domain.User;
import com.seekandbuy.haveabeer.exceptions.UserNotFoundException;
import com.seekandbuy.haveabeer.validator.ValidatorBeer;
import com.seekandbuy.haveabeer.validator.ValidatorBeerUser;

@Service
public class UserBeerService extends GenericService<BeerUser>
{	
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<BeerUser> listItem()
	{	
		return userDao.findAll();  
	}
	
	public UserBeerService() {
		super.validateItem = new ValidatorBeerUser();
	}
	
	@Override
	public Optional<BeerUser> findItem(Long id)
	{
		Optional<BeerUser> user = userDao.findById(id);
		
		if(user == null)
		{
			throw new UserNotFoundException("User can not be found");
		}
		
		return user;
	}
	
	public static <T> List<T> toList(Optional<T> option) 
	{
	    if (option.isPresent())
	        return Collections.singletonList(option.get());
	    else
	        return Collections.emptyList();
	}
		
	@Override
	public boolean createItem(BeerUser user) 
	{	
		user.setId(null); //Garantir que criaremos uma instância nova e não atualizaremos nenhuma
		user.getBeerCharacteristic().setId(null);		

		
		if(validateItem(user))
		{		
			String password = user.getPassword();
					
			String token = auth.tokenizerPassword(password);
			user.setPassword(token);
			
			userDao.save(user);
			return true;
		}
		else
			return false;
	}
	
	@Override
	public void deleteItem(Long id) 
	{
		try 
		{
			userDao.deleteById(id);
		}
		catch(EmptyResultDataAccessException e)
		{
			throw new UserNotFoundException("User can not be found");
		}
	}
	
	@Override
	public void updateItem(BeerUser user)
	{
		verifyExistence(user);
		userDao.save(user);
	}
	
	//Semântica melhor, só verifica existência 
	@Override
	public void verifyExistence(BeerUser user)
	{
		findItem(user.getId());
	}	
		
	public User findUser(String password, String email)
	{
		User user = findUserByEmail(email);
		
		auth.findTokenByPassword(password);
		
		//Se o usuário é encontrado pelo email, então verifica a senha.
		//Se não houverem exceções, então retorna o usuário
		return user;	
	}
	
	private User findUserByEmail(String email)
	{
		User user;
		
		user = userDao.findUserByEmail(email);
		
		if(user == null)
		{
			throw new UserNotFoundException("User can not be found");
		}		
		return user;
	}
}
