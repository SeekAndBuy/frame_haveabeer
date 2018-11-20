package com.seekandbuy.haveabeer.strategytolist;

import java.util.List;

//import com.seekandbuy.haveabeer.dao.ProductDao;
import com.seekandbuy.haveabeer.domain.Product;

public interface SearchItems {
	
	public List<Product>listAllProducts();
	public List<Product>ListAllProductsByUser(Long id);

}
