
package com.seekandbuy.haveabeer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.seekandbuy.haveabeer.domain.Product;

@CrossOrigin(origins="http://localhost:4200")
@Repository
public interface GenericDao extends JpaRepository<Product, Long> {

}
