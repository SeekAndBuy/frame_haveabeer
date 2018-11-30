package com.seekandbuy.haveabeer.services;

import com.seekandbuy.haveabeer.match.SearchBeer;
import com.seekandbuy.haveabeer.notification.NotificationBeer;

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
	
	public Beer createItemAndNotifyUser(Beer product, List<BeerUser> listOfUsers) {
		NotificationBeer notificationBeer = new NotificationBeer();
		Beer beer = this.createItem(product);
		
		notificationBeer.sendNotification(product, listOfUsers);
		return beer;
	}
	
	@Override
	public Beer createItem(Beer product) 
	{
		product.setId(null); //Garantir que criaremos uma instância nova e não atualizaremos nenhuma	
		product.getBeerCharacteristic().setId(null);
		return productDao.save(product);	
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
	public void updateItem(Beer promotion)
	{
		verifyExistence(promotion);
		productDao.save(promotion);
	}
	

	@Override
	public void verifyExistence(Beer promotion)
	{
		findItem(promotion.getId());
	}
	
	public List<Beer> getPromotionByUserId(Long id) 
	{
		return productDao.getPromotionByUserId(id);
	}
	
}
