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

import com.seekandbuy.haveabeer.domain.Job;
import com.seekandbuy.haveabeer.domain.CandidateUser;
import com.seekandbuy.haveabeer.dao.JobDao;
//import com.seekandbuy.haveabeer.dao.UserDao;
//import com.seekandbuy.haveabeer.dao.UserDao;
import com.seekandbuy.haveabeer.exceptions.ProductNotFoundException;

@Service
public class ProductJobService extends GenericService<Job>
{	
	@Autowired
	private JobDao productDao;
	
	SearchBeer searchBeer = new SearchBeer();
	
	public List<Job> listItemByUserCharacteristic(CandidateUser user, List<Job> allBeers){

		return searchBeer.ListAllProductsByUser(user, allBeers);
	}
	
	@Override
	public List<Job> listItem()
	{
		return productDao.findAll();  
	}
	
	@Override
	public Optional<Job> findItem(Long id)
	{
		Optional<Job> promotion = productDao.findById(id);
		
		if(promotion == null)
		{
			throw new ProductNotFoundException("Job can not be found");
		}
		
		return promotion;
	}
	
	public Job createItemAndNotifyUser(Job product, List<CandidateUser> listOfUsers) {
		NotificationBeer notificationBeer = new NotificationBeer();
		Job beer = this.createItem(product);
		
		notificationBeer.sendNotification(product, listOfUsers);
		return beer;
	}
	
	@Override
	public Job createItem(Job product) 
	{
		product.setId(null); //Garantir que criaremos uma instância nova e não atualizaremos nenhuma	
		product.getJobCharacteristic().setId(null);
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
			throw new ProductNotFoundException("Jog can not be found");
		}
	}
	
	@Override
	public void updateItem(Job job)
	{
		verifyExistence(job);
		productDao.save(job);
	}
	

	@Override
	public void verifyExistence(Job job)
	{
		findItem(job.getId());
	}
	
	public List<Job> getPromotionByUserId(Long id) 
	{
		return productDao.getPromotionByUserId(id);
	}
	
}
