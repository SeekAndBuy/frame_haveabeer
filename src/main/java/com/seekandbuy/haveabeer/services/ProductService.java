package com.seekandbuy.haveabeer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.seekandbuy.haveabeer.dao.ProductDao;
import com.seekandbuy.haveabeer.domain.Product;
import com.seekandbuy.haveabeer.exceptions.ProductNotFoundException;

@Service
public class ProductService 
{
	
	@Autowired
	private ProductDao promotionDao;
	
	
	public List<Product> listar()
	{
		return promotionDao.findAll();  
	}
	 
	public Optional<Product> findPromotion(Long id)
	{
		Optional<Product> promotion = promotionDao.findById(id);
		
		if(promotion == null)
		{
			throw new ProductNotFoundException("Promotion can not be found");
		}
		
		return promotion;
	}
	
	public Product promotionCreate(Product promotion) 
	{
		promotion.setId(null); //Garantir que criaremos uma instância nova e não atualizaremos nenhuma		
		return promotionDao.save(promotion);	
	}
	
	public void deletePromotion(Long id) 
	{
		try 
		{
			promotionDao.deleteById(id);
		}
		catch(EmptyResultDataAccessException e)
		{
			throw new ProductNotFoundException("Promotion can not be found");
		}
	}
	
	public void updatePromotion(Product promotion)
	{
		verifyExistence(promotion);
		promotionDao.save(promotion);
	}
	
	//Semântica melhor, só verifica existência 
	public void verifyExistence(Product promotion)
	{
		findPromotion(promotion.getId());
	}
	
	public List<Product> getPromotionByUserId(Long id) 
	{
		return promotionDao.getPromotionByUserId(id);
	}
	
}
