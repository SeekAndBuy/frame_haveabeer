package com.seekandbuy.haveabeer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.seekandbuy.haveabeer.domain.Promotion;

@CrossOrigin(origins="http://localhost:4200")
@Repository
public interface PromotionDao extends JpaRepository<Promotion, Long>
{
	@Query("SELECT p FROM Promotion p WHERE p.Id = :userId")
	public List<Promotion> getPromotionByUserId(@Param("userId") Long id);
}
