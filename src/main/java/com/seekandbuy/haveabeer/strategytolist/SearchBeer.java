package com.seekandbuy.haveabeer.strategytolist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.seekandbuy.haveabeer.dao.ProductDao;
import com.seekandbuy.haveabeer.domain.Product;

public class SearchBeer implements SearchItems {

	@Autowired
	private ProductDao promotionDao;
	

	@Override
	public List<Product> listAllProducts() {
		return promotionDao.findAll();
	}

	@Override
	public List<Product> ListAllProductsByUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
