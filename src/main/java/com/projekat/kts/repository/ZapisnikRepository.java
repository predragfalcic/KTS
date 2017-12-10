package com.projekat.kts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.kts.model.Sednica;
import com.projekat.kts.model.Zapisnik;

public interface ZapisnikRepository extends JpaRepository<Zapisnik, Long> {
	
	public List<Zapisnik> findBySednica(Sednica sednica);
	
}
