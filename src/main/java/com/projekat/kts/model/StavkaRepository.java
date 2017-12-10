package com.projekat.kts.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StavkaRepository extends JpaRepository<Stavka, Long> {
	public List<Stavka> findBySednica(Sednica sednica);
}
