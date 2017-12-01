package com.projekat.kts.dto;

import java.util.List;
import java.util.Set;

import com.projekat.kts.model.Apartmen;
import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Building;

/**
 * class that contains all users and all buildings
 * it will be returned when the admin wants to access 
 * the buildings page
 * @author Falca
 *
 */
public class TenatsApartmenDTO {

	private List<Apartmen> apartmens;
	private List<AppUser> tenats;
	private Set<AppUser> tenatsFromApartmen;
	private List<Building> buildings;
	private Apartmen apartmen;
	
	public TenatsApartmenDTO() {}

	public List<Apartmen> getApartmens() {
		return apartmens;
	}

	public void setApartmens(List<Apartmen> apartmens) {
		this.apartmens = apartmens;
	}

	public List<AppUser> getTenats() {
		return tenats;
	}

	public void setTenats(List<AppUser> tenats) {
		this.tenats = tenats;
	}

	public Set<AppUser> getTenatsFromApartmen() {
		return tenatsFromApartmen;
	}

	public void setTenatsFromApartmen(Set<AppUser> tenatsFromApartmen) {
		this.tenatsFromApartmen = tenatsFromApartmen;
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

	public Apartmen getApartmen() {
		return apartmen;
	}

	public void setApartmen(Apartmen apartmen) {
		this.apartmen = apartmen;
	}
	
}
