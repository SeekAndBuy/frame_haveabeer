package com.seekandbuy.haveabeer.dao;

import java.util.List;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.bind.annotation.CrossOrigin;
import com.seekandbuy.haveabeer.domain.Address;


public interface AddressDao extends GenericDao
{
	@Query("SELECT a FROM Address a WHERE a.Id = :userId")
	public List<Address> getAddressByUserId(@Param("userId") Long id);
}
