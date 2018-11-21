/*Promotion Controller*/
package com.seekandbuy.haveabeer.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seekandbuy.haveabeer.domain.Product;
//import com.seekandbuy.haveabeer.domain.User;
import com.seekandbuy.haveabeer.exceptions.ProductNotFoundException;
import com.seekandbuy.haveabeer.exceptions.UserNotFoundException;
import com.seekandbuy.haveabeer.services.ProductService;


@RestController
@RequestMapping("/promotions")
@CrossOrigin(origins="http://localhost:4200")
public class ProductResources implements GenericResources<Product>
{
	private ProductService promotionService;
	
	public ProductResources(ProductService promotionService) 
	{
		this.promotionService = promotionService;
	}

	@Override
	public ResponseEntity<List<Product>> listItem() {
		return ResponseEntity.status(HttpStatus.OK).body(promotionService.listItem());
	}

	@Override
	public ResponseEntity<Void> createItem(Product promotion) {
		promotion = promotionService.createItem(promotion);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(promotion.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

	@Override
	public ResponseEntity<Optional<Product>> findItem(Long id) {
		Optional<Product> promotion = null;
		try
		{
			promotion = promotionService.findItem(id);
		}catch(ProductNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(promotion);
	}

	@Override
	public ResponseEntity<Void> deleteItem(Long id) {
		try
		{
			promotionService.deleteItem(id);
		}
		catch(ProductNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> updateItem(Product product, Long id) {
		product.setId(id); // Garantir que o que vai ser atualizado é o que está vindo na URI
		try
		{
			promotionService.updateItem(product);
		}
		catch(ProductNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> findPromotionByUserId(@PathVariable("id") Long id){
		List<Product> userPromotions = null;
		
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
