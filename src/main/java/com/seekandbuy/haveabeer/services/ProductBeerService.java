package com.seekandbuy.haveabeer.services;

import com.seekandbuy.haveabeer.match.SearchBeer;
import com.seekandbuy.haveabeer.notification.NotificationBeer;
import com.seekandbuy.haveabeer.validator.ValidatorBeer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;

import com.seekandbuy.haveabeer.domain.Beer;
import com.seekandbuy.haveabeer.domain.BeerUser;
import com.seekandbuy.haveabeer.dao.ProductDao;
//import com.seekandbuy.haveabeer.dao.UserDao;
//import com.seekandbuy.haveabeer.dao.UserDao;
import com.seekandbuy.haveabeer.exceptions.ProductNotFoundException;

@Service
public class ProductBeerService extends GenericService<Beer>
{	
	
	public ProductBeerService() {
		super.validateItem = new ValidatorBeer();
	}
	
	@Autowired
	private ProductDao productDao;
	
	SearchBeer searchBeer = new SearchBeer();
	
	public List<Beer> listItemByUserCharacteristic(BeerUser user, List<Beer> allBeers){

		return searchBeer.ListAllProductsByUser(user, allBeers);
	}
	
	@Override
	public List<Beer> listItem()
	{
		return productDao.findAll();  
	}
	
	@Override
	public Optional<Beer> findItem(Long id)
	{
		Optional<Beer> promotion = productDao.findById(id);
		
		if(promotion == null)
		{
			throw new ProductNotFoundException("Promotion can not be found");
		}
		
		return promotion;
	}
	
	public boolean createItemAndNotifyUser(Beer product, List<BeerUser> listOfUsers) {
		NotificationBeer notificationBeer = new NotificationBeer();
		
		if(createItem(product))
		{
			notificationBeer.sendNotification(product, listOfUsers);
			return true;
		}
		else
			return false;		
	}
	
	@Override
	public boolean createItem(Beer product) 
	{
		if(validateItem(product))
		{		
			product.setId(null); //Garantir que criaremos uma instância nova e não atualizaremos nenhuma	
			product.getBeerCharacteristic().setId(null);
			productDao.save(product);
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
			productDao.deleteById(id);
		}
		catch(EmptyResultDataAccessException e)
		{
			throw new ProductNotFoundException("Promotion can not be found");
		}
	}
	
	@Override
	public void updateItem(Beer beer)
	{
		verifyExistence(beer);
		productDao.save(beer);
	}	

	@Override
	public void verifyExistence(Beer beer)
	{
		findItem(beer.getId());
	}
	
	public List<Beer> getBeerByUserId(Long id) 
	{
		return productDao.getBeerByUserId(id);
	}
	
}
