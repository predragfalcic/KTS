package com.projekat.kts.dto;

import java.util.List;
import java.util.Set;

import com.projekat.kts.model.Building;
import com.projekat.kts.model.Institution;

/**
 * Class that contains all the buildings
 * and institutions and is used to display data to the 
 * user
 * @author Falca
 *
 */
public class InstitutionBuildingsDTO {
	
	private List<Institution> institutions;
	private List<Building> buildings;
	private Set<Building> institutionBuildings; // Zgrade koje trenutno odrzava institucija
	
	public InstitutionBuildingsDTO() {}

	public List<Institution> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(List<Institution> institutions) {
		this.institutions = institutions;
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

	public Set<Building> getInstitutionBuildings() {
		return institutionBuildings;
	}

	public void setInstitutionBuildings(Set<Building> institutionBuildings) {
		this.institutionBuildings = institutionBuildings;
	}
	
}
