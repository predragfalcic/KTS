package com.projekat.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.kts.model.Building;

public interface BuildingRepository extends JpaRepository<Building, Long> {
	public Building findOneByName(String name);
}
