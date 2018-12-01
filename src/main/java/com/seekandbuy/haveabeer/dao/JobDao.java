package com.seekandbuy.haveabeer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.seekandbuy.haveabeer.domain.Job;
import com.seekandbuy.haveabeer.domain.Product;

@CrossOrigin(origins="http://localhost:4200")
@Repository
public interface JobDao extends GenericDao, JpaRepository<Job, Long>
{
	@Query("SELECT p FROM Beer p WHERE USERID(p.Id) = USERID(:userId)")
	public List<Job> getPromotionByUserId(@Param("userId") Long id);
}
