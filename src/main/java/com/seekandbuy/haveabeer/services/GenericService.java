package com.seekandbuy.haveabeer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.seekandbuy.haveabeer.auth.Authentication;
import com.seekandbuy.haveabeer.notification.Notification;
import com.seekandbuy.haveabeer.validator.Validator;

@Service
public abstract class GenericService<T> 
{
	public Validator<T> validateItem;
	
	public Authentication auth = new Authentication();
	
	public abstract List<T> listItem();
	 
	public abstract Optional<T> findItem(Long id);
	
	public abstract T createItem(T itensList); 
	
	public abstract void deleteItem(Long id);
	
	public abstract void updateItem(T item);
	
	public abstract void verifyExistence(T item);		
}
