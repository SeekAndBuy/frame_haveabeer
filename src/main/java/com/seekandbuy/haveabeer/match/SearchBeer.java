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
import com.seekandbuy.haveabeer.domain.Job;
import com.seekandbuy.haveabeer.domain.JobCharacteristic;
import com.seekandbuy.haveabeer.domain.CandidateUser;

public class SearchBeer extends SearchItems<CandidateUser, Job> {
	
	@Override
	public List<Job> ListAllProductsByUser(CandidateUser user, List<Job> allBeers) {
		//System.out.println(id);
		//Optional<BeerUser> user = userDao.findById(id);
		
		//BeerUser userbeer = (BeerUser) user.get();
		JobCharacteristic characteristicUser = user.getBeerCharacteristic();
		
		//System.out.println(characteristicUser.getBrand());
		//System.out.println(characteristicUser.getPrice());
		
		//classe para armazenar a tupla <product, quantidade de matchs>
		class CharacteristicAndMatchs{
			public Job beer;
			public int matchValue; 
			
			public CharacteristicAndMatchs(Job beer, int matchValue) {
				this.beer = beer;
				this.matchValue =matchValue;
			}
			public Job getBeer() {
				return this.beer;
			}
			
			public int getMatchValue() {
				return this.matchValue;
			}
		}
		
		class SortbyBeer implements Comparator<Job> 
		{ 
		    // Used for sorting in ascending order of 
		    // roll number 
			@Override
			public int compare(Job b1, Job b2) {
				
				double value = b1.getJobCharacteristic().getSalario() - b2.getJobCharacteristic().getSalario();
				
				if(value < 0)
					return -1;
				if(value > 0)
					return 1;
				else
					return 0;
			} 
		}
		
		List<CharacteristicAndMatchs> characteristicMatchs = new ArrayList<CharacteristicAndMatchs>();
		List<Job> productsByUser = new ArrayList<Job>();
		
		Collections.sort(allBeers, new SortbyBeer());
		
		for(Job p: allBeers) {
			
			JobCharacteristic characteristicProduct = (JobCharacteristic) p.getJobCharacteristic();
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
	private int countMatchs(JobCharacteristic charaUser, JobCharacteristic charaBeer) {
		int equal = 0;
		
		if(charaUser.getEscolaridade().equals(charaBeer.getEscolaridade()))
			equal += 2;
		if(charaBeer.getSalario() <= charaUser.getSalario())
			equal++;
		
		System.out.println(charaBeer.getEscolaridade());
		System.out.println(charaBeer.getSalario());
		System.out.println(equal);
		System.out.println();
		
		return equal;
	}

}