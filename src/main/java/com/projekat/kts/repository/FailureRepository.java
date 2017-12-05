package com.projekat.kts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Building;
import com.projekat.kts.model.Failure;

public interface FailureRepository extends JpaRepository<Failure, Long> {
	public List<Failure> findByBuilding(Building building);
	public List<Failure> findByWorker(AppUser worker);
}
