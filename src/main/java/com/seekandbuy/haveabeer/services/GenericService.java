package com.seekandbuy.haveabeer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.seekandbuy.haveabeer.auth.Authentication;

@Service
public interface GenericService<T> 
{
	public Authentication auth = new Authentication();
	
	public List<T> listItem();
	 
	public Optional<T> findItem(Long id);
	
	public T createItem(T itensList); 
	
	public void deleteItem(Long id);
	
	public void updateItem(T item);
	
	public void verifyExistence(T item);		
}
