/*Promotion Controller*/
package com.seekandbuy.haveabeer.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seekandbuy.haveabeer.HaveabeerApplication;
import com.seekandbuy.haveabeer.domain.Beer;
import com.seekandbuy.haveabeer.domain.BeerUser;
//import com.seekandbuy.haveabeer.domain.Product;
//import com.seekandbuy.haveabeer.domain.User;
import com.seekandbuy.haveabeer.exceptions.ProductNotFoundException;
import com.seekandbuy.haveabeer.exceptions.UserNotFoundException;
import com.seekandbuy.haveabeer.services.ProductBeerService;
import com.seekandbuy.haveabeer.services.UserBeerService;


@RestController
@RequestMapping("/promotions")
@CrossOrigin(origins="http://localhost:4200")
public class ProductBeerResources implements GenericResources<Beer>
{
	@Autowired
	private ProductBeerService productService;
	
	@Autowired
	private UserBeerService userService;
	
	public ProductBeerResources(ProductBeerService productService, UserBeerService userService) 
	{
		this.productService = productService;
		this.userService = userService;
	}

	@Override
	public ResponseEntity<List<Beer>> listItem() {
		return ResponseEntity.status(HttpStatus.OK).body(productService.listItem());
	}

	@Override
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createItem(@RequestBody Beer product) {
		product = productService.createItem(product);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(product.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/createandnotify/", method = RequestMethod.POST)
	public ResponseEntity<Void>  createAndNotify(@RequestBody Beer product){
		List<BeerUser> allUsers = null;	
		allUsers = userService.listItem();
		
		product = productService.createItemAndNotifyUser(product, allUsers);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(product.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

	@Override
	public ResponseEntity<Optional<Beer>> findItem(Long id) {
		Optional<Beer> promotion = null;
		try
		{
			promotion = productService.findItem(id);
		}catch(ProductNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(promotion);
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteItem(@PathVariable("id") Long id) {
		try
		{
			productService.deleteItem(id);
		}
		catch(ProductNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> updateItem(Beer product, Long id) {
		product.setId(id); // Garantir que o que vai ser atualizado é o que está vindo na URI
		try
		{
			productService.updateItem(product);
		}
		catch(ProductNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Beer>> findPromotionByUserId(@PathVariable("id") Long id){
		List<Beer> userPromotions = null;
		
		try
		{
			userPromotions = productService.getPromotionByUserId(id);
		}
		catch(UserNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(userPromotions);
	}
	
	@RequestMapping(value = "/bycharacteristics/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Beer>> findBeerByUserCharacteristic(@PathVariable("id") Long id){
		List<Beer> productsByCharacteristic = null;
		
		List<Beer> allBeers = null;
		Optional<BeerUser> userBeer = null;		
		
		try
		{
			userBeer = userService.findItem(id);
			BeerUser user = (BeerUser) userBeer.get();
			
			allBeers = productService.listItem();
			
			productsByCharacteristic = productService.listItemByUserCharacteristic(user, allBeers);
		}
		catch(UserNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(productsByCharacteristic);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HaveabeerApplication.class, args);
	}
	
}


