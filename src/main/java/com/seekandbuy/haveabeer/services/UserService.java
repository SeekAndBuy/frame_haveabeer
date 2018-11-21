package com.seekandbuy.haveabeer.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.seekandbuy.haveabeer.dao.UserDao;
import com.seekandbuy.haveabeer.domain.User;
import com.seekandbuy.haveabeer.exceptions.UserNotFoundException;

@Service
public class UserService implements GenericService<User>
{	
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> listItem()
	{
		return userDao.findAll();  
	}
	
	@Override
	public Optional<User> findItem(Long id)
	{
		Optional<User> user = userDao.findById(id);
		
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
	public User createItem(User user) 
	{	
		user.setId(null); //Garantir que criaremos uma instância nova e não atualizaremos nenhuma
		String password = user.getPassword();
				
		String token = auth.tokenizerPassword(password);
		user.setPassword(token);
		
		return userDao.save(user);
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
	public void updateItem(User user)
	{
		verifyExistence(user);
		userDao.save(user);
	}
	
	//Semântica melhor, só verifica existência 
	@Override
	public void verifyExistence(User user)
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
