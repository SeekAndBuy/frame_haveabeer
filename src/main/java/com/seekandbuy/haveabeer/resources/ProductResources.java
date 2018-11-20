/*Promotion Controller*/
package com.seekandbuy.haveabeer.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seekandbuy.haveabeer.domain.Product;
import com.seekandbuy.haveabeer.domain.User;
import com.seekandbuy.haveabeer.exceptions.ProductNotFoundException;
import com.seekandbuy.haveabeer.exceptions.UserNotFoundException;
import com.seekandbuy.haveabeer.services.ProductService;

@RestController
@RequestMapping("/promotions")
@CrossOrigin(origins="http://localhost:4200")
public class ProductResources 
{	
	@Autowired
	private ProductService promotionService;
	
	public ProductResources(ProductService promotionService) 
	{
		this.promotionService = promotionService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Product>> ListPromotions() 
	{
		return ResponseEntity.status(HttpStatus.OK).body(promotionService.listar());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> promotionCreate(@RequestBody Product promotion) 
	{
		promotion = promotionService.promotionCreate(promotion);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(promotion.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Product>> findPromotion(@PathVariable("id") Long id)
	{
		Optional<Product> promotion = null;
		try
		{
			promotion = promotionService.findPromotion(id);
		}catch(ProductNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(promotion);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePromotion(@PathVariable("id") Long id) 
	{ 
		try
		{
			promotionService.deletePromotion(id);
		}
		catch(ProductNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updatePromotion(@RequestBody Product promotion, @PathVariable("id") Long id)
	{
		promotion.setId(id); // Garantir que o que vai ser atualizado é o que está vindo na URI
		try
		{
			promotionService.updatePromotion(promotion);
		}
		catch(ProductNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> findPromotionByUserId(@PathVariable("id") Long id) {
		
		List<Product> userPromotions = null;
		
		//return ResponseEntity.status(HttpStatus.OK).body(promotionService.listar());
		try
		{
			userPromotions = promotionService.getPromotionByUserId(id);
		}
		catch(UserNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(userPromotions);
	}
	
}
