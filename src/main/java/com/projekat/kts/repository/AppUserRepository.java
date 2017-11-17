package com.projekat.kts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.kts.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{

	public AppUser findOneByUsername(String username);
	public List<AppUser> findByRolesAndHasBuilding(String role, boolean hasBuilding);
}
