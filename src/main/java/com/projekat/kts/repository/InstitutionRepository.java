package com.projekat.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.kts.model.Institution;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

	public Institution findOneByName(String name);
}
