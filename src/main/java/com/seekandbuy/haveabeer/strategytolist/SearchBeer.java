package com.seekandbuy.haveabeer.strategytolist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.CrudRepository;

import com.seekandbuy.haveabeer.dao.ProductDao;
import com.seekandbuy.haveabeer.dao.UserDao;
import com.seekandbuy.haveabeer.domain.BeerCharacteristic;
import com.seekandbuy.haveabeer.domain.BeerUser;
import com.seekandbuy.haveabeer.domain.Product;
import com.seekandbuy.haveabeer.domain.User;

public class SearchBeer implements SearchItems {

	@Autowired
	private ProductDao promotionDao;
	private UserDao userDao;
	

	@Override
	public List<Product> listAllProducts() {
		return promotionDao.findAll();
	}

	@Override
	public List<Product> ListAllProductsByUser(Long id) {
		Optional<User> user = userDao.findById(id);
		
		BeerUser userbeer = (BeerUser) user.get();
		BeerCharacteristic characteristicUser = (BeerCharacteristic) userbeer.getUserCharacteristics();
		
		//classe para armazenar a tupla <product, quantidade de matchs>
		class CharacteristicAndMatchs{
			public Product product;
			public int matchValue; 
			
			public CharacteristicAndMatchs(Product product, int matchValue) {
				this.product = product;
				this.matchValue =matchValue;
			}
			public Product getProduct() {
				return this.product;
			}
			
			public int getMatchValue() {
				return this.matchValue;
			}
		}
		
		List<CharacteristicAndMatchs> characteristicMatchs = new ArrayList<CharacteristicAndMatchs>();
		List<Product> productsByUser = new ArrayList<Product>();
		
		for(Product p: this.listAllProducts()) {
			BeerCharacteristic characteristicProduct = (BeerCharacteristic) p.getProductCharacteristics();
			int matchSize = this.countMatchs(characteristicUser, characteristicProduct);
			CharacteristicAndMatchs matchCharacter = new CharacteristicAndMatchs(p, matchSize);
			characteristicMatchs.add(matchCharacter);
		}
		
		Collections.sort(characteristicMatchs, 
				Comparator.comparingInt(CharacteristicAndMatchs::getMatchValue).reversed()); //ordenando em ordem decrescente
		
		for(CharacteristicAndMatchs c : characteristicMatchs) //armazenando apenas os produtos em productByUser
		{
			productsByUser.add(c.getProduct());
		}
		
		
		return productsByUser;
	}
	
	//Metodo auxiliar para contar os matchs das caracteristicas do produto com as do usuario
	private int countMatchs(BeerCharacteristic charaUser, BeerCharacteristic charaBeer) {
		int equal = 0;
		
		if(charaUser.getBrand().equals(charaBeer.getBrand()))
			equal++;
		if(charaUser.getPrice() == charaBeer.getPrice())
			equal++;
		
		return equal;
	}

}
