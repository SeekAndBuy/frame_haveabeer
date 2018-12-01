package com.seekandbuy.haveabeer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.seekandbuy.haveabeer.domain.Beer;
import com.seekandbuy.haveabeer.domain.Product;

@CrossOrigin(origins="http://localhost:4200")
@Repository
public interface ProductDao extends GenericDao, JpaRepository<Beer, Long>
{
	@Query("SELECT p FROM Beer p WHERE USERID(p.Id) = USERID(:userId)")
	public List<Beer> getBeerByUserId(@Param("userId") Long id);
}
