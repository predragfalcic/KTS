package com.projekat.kts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.kts.model.Building;
import com.projekat.kts.model.Sednica;

public interface SednicaRepository extends JpaRepository<Sednica, Long> {

	public List<Sednica> findAll();
	public List<Sednica> findByBuilding(Building building);
}
