package com.projekat.kts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.kts.model.Apartmen;

public interface ApartmentRepository extends JpaRepository<Apartmen, Long> {
	public Apartmen findOneByName(String name);
	public List<Apartmen> findByHasApartmentBuilding(boolean hasBuilding);
}
