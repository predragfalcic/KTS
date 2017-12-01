package com.projekat.kts.dto;

import java.util.List;
import java.util.Set;

import com.projekat.kts.model.Apartmen;
import com.projekat.kts.model.Building;

/**
 * class that contains all users and all buildings
 * it will be returned when the admin wants to access 
 * the buildings page
 * @author Falca
 *
 */
public class ApartmentsBuildingDTO {

	private List<Building> buildings;
	private List<Apartmen> apartmens;
	private Set<Apartmen> apartmensFromBuilding;
	private Building building;
	
	public ApartmentsBuildingDTO() {}

	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

	public List<Apartmen> getApartmens() {
		return apartmens;
	}

	public void setApartmens(List<Apartmen> apartmens) {
		this.apartmens = apartmens;
	}

	public Set<Apartmen> getApartmensFromBuilding() {
		return apartmensFromBuilding;
	}

	public void setApartmensFromBuilding(Set<Apartmen> apartmensFromBuilding) {
		this.apartmensFromBuilding = apartmensFromBuilding;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}
	
}
