package com.seekandbuy.haveabeer.notification;

import java.util.List;

import com.seekandbuy.haveabeer.domain.Beer;
import com.seekandbuy.haveabeer.domain.BeerCharacteristic;
import com.seekandbuy.haveabeer.domain.BeerUser;

public class NotificationBeer extends Notification<BeerUser, Beer>{

	@Override
	public void sendNotification(Beer product, List<BeerUser> listOfUsers) {
		int whenNotification = 2; //Se o produto tiver mais de dois pontos de interesse, o usuario sera notificado.
		BeerCharacteristic beerCharacteristic = product.getBeerCharacteristic();
		
		for(BeerUser u: listOfUsers) {
			int matchs = this.countMatchs(u.getBeerCharacteristic(), beerCharacteristic);
			if(matchs>whenNotification) {
				sendMail.sendNotification("seekandbuyorganization@gmail.com",
						"12345678organization", u.getEmail(), "Seek and Buy", "Hey there! \n\n We have a new product for you =]");
			}
		}
		
	}
	
	private int countMatchs(BeerCharacteristic charaUser, BeerCharacteristic charaBeer) {
		int equal = 0;
		
		if(charaUser.getBrand().equals(charaBeer.getBrand()))
			equal += 2;
		if(charaBeer.getPrice() <= charaUser.getPrice())
			equal++;
		
		System.out.println(charaBeer.getBrand());
		System.out.println(charaBeer.getPrice());
		System.out.println(equal);
		System.out.println();
		
		return equal;
	}

}
