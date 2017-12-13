package com.projekat.kts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.kts.model.Sednica;
import com.projekat.kts.model.Stavka;

public interface StavkaRepository extends JpaRepository<Stavka, Long> {
	public List<Stavka> findBySednica(Sednica sednica);
}
