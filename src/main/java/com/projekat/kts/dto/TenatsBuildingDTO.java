package com.projekat.kts.dto;

import java.util.List;
import java.util.Set;

import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Building;

/**
 * class that contains all users and all buildings
 * it will be returned when the admin wants to access 
 * the buildings page
 * @author Falca
 *
 */
public class TenatsBuildingDTO {

	private List<Building> buildings;
	private List<AppUser> tenats;
	private Set<AppUser> tenatsFromBuilding;
	
	public TenatsBuildingDTO() {}

	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

	public List<AppUser> getTenats() {
		return tenats;
	}

	public void setTenats(List<AppUser> tenats) {
		this.tenats = tenats;
	}

	public Set<AppUser> getTenatsFromBuilding() {
		return tenatsFromBuilding;
	}

	public void setTenatsFromBuilding(Set<AppUser> tenatsFromBuilding) {
		this.tenatsFromBuilding = tenatsFromBuilding;
	}
	
	
}
