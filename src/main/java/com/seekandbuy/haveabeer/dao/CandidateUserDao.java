package com.seekandbuy.haveabeer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.seekandbuy.haveabeer.domain.CandidateUser;
//import com.seekandbuy.haveabeer.domain.User;

@CrossOrigin(origins="http://localhost:4200")
@Repository
public interface CandidateUserDao extends GenericDao, JpaRepository<CandidateUser, Long>{

	@Query("SELECT u FROM CandidateUser u WHERE u.email = :userEmail")
	public CandidateUser findUserByEmail(@Param("userEmail") String email);
	
}
