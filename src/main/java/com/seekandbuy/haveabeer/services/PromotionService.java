package com.seekandbuy.haveabeer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.seekandbuy.haveabeer.dao.PromotionDao;
import com.seekandbuy.haveabeer.domain.Promotion;
import com.seekandbuy.haveabeer.exceptions.PromotionNotFoundException;

@Service
public class PromotionService 
{
	
	@Autowired
	private PromotionDao promotionDao;
	
	
	public List<Promotion> listar()
	{
		return promotionDao.findAll();  
	}
	 
	public Optional<Promotion> findPromotion(Long id)
	{
		Optional<Promotion> promotion = promotionDao.findById(id);
		
		if(promotion == null)
		{
			throw new PromotionNotFoundException("Promotion can not be found");
		}
		
		return promotion;
	}
	
	public Promotion promotionCreate(Promotion promotion) 
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
			throw new PromotionNotFoundException("Promotion can not be found");
		}
	}
	
	public void updatePromotion(Promotion promotion)
	{
		verifyExistence(promotion);
		promotionDao.save(promotion);
	}
	
	//Semântica melhor, só verifica existência 
	public void verifyExistence(Promotion promotion)
	{
		findPromotion(promotion.getId());
	}
	
	public List<Promotion> getPromotionByUserId(Long id) 
	{
		return promotionDao.getPromotionByUserId(id);
	}
	
}
