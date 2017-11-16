package com.projekat.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.kts.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{

	public AppUser findOneByUsername(String username);
	
}
