package com.seekandbuy.haveabeer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.bind.annotation.CrossOrigin;

import com.seekandbuy.haveabeer.domain.Product;
import com.seekandbuy.haveabeer.domain.User;


public interface UserDao extends GenericDao, JpaRepository<User, Long>
{
	@Query("SELECT u FROM Promotion u WHERE u.email = :userEmail")
	public User findUserByEmail(@Param("userEmail") String email);
}
