package com.seekandbuy.haveabeer.match;

import java.util.List;

//import com.seekandbuy.haveabeer.domain.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.util.Optional;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.CrudRepository;

//import com.seekandbuy.haveabeer.dao.ProductDao;
//import com.seekandbuy.haveabeer.dao.UserDao;
import com.seekandbuy.haveabeer.domain.Beer;
import com.seekandbuy.haveabeer.domain.BeerCharacteristic;
import com.seekandbuy.haveabeer.domain.BeerUser;

public class SearchBeer extends SearchItems<BeerUser, Beer> {
	
	@Override
	public List<Beer> ListAllProductsByUser(BeerUser user, List<Beer> allBeers) {
	
		BeerCharacteristic characteristicUser = user.getBeerCharacteristic();
		
		//classe para armazenar a tupla <product, quantidade de matchs>
		class CharacteristicAndMatchs{
			public Beer beer;
			public int matchValue; 
			
			public CharacteristicAndMatchs(Beer beer, int matchValue) {
				this.beer = beer;
				this.matchValue =matchValue;
			}
			public Beer getBeer() {
				return this.beer;
			}
			
			public int getMatchValue() {
				return this.matchValue;
			}
		}
		
		class SortbyBeer implements Comparator<Beer> 
		{ 
		    // Used for sorting in ascending order of 
		    // roll number 
			@Override
			public int compare(Beer b1, Beer b2) {
				
				double value = b1.getBeerCharacteristic().getPrice() - b2.getBeerCharacteristic().getPrice();
				
				if(value < 0)
					return -1;
				if(value > 0)
					return 1;
				else
					return 0;
			} 
		}
		
		List<CharacteristicAndMatchs> characteristicMatchs = new ArrayList<CharacteristicAndMatchs>();
		List<Beer> productsByUser = new ArrayList<Beer>();
		
		Collections.sort(allBeers, new SortbyBeer());
		
		for(Beer p: allBeers) {
			
			BeerCharacteristic characteristicProduct = (BeerCharacteristic) p.getBeerCharacteristic();
			int matchSize = this.countMatchs(characteristicUser, characteristicProduct);		
			
			CharacteristicAndMatchs matchCharacter = new CharacteristicAndMatchs(p, matchSize);
			characteristicMatchs.add(matchCharacter);
		}
		
		Collections.sort(characteristicMatchs, 
		Comparator.comparingInt(CharacteristicAndMatchs::getMatchValue).reversed()); //ordenando em ordem decrescente
		
		for(CharacteristicAndMatchs c : characteristicMatchs) //armazenando apenas os produtos em productByUser
		{
			productsByUser.add(c.getBeer());
		}
		
		
		return productsByUser;
	}
	
	//Metodo auxiliar para contar os matchs das caracteristicas do produto com as do usuario
	private int countMatchs(BeerCharacteristic charaUser, BeerCharacteristic charaBeer) {
		int equal = 0;
		
		if(charaUser.getBrand().equals(charaBeer.getBrand()))
			equal += 2;
		if(charaBeer.getPrice() <= charaUser.getPrice())
			equal++;
		
		return equal;
	}

}