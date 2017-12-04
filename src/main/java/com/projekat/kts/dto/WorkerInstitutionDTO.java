package com.projekat.kts.dto;

import java.util.List;
import java.util.Set;

import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Building;
import com.projekat.kts.model.Institution;

public class WorkerInstitutionDTO {

	private List<Institution> institutions;
	private List<AppUser> workers;
	private Set<AppUser> workersFromInstitution;
	private Institution institution;
	private List<Building> buildings;
	private Set<Building> institutionBuildings; // Zgrade koje trenutno odrzava institucija
	
	public WorkerInstitutionDTO() {}

	public List<Institution> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(List<Institution> institutions) {
		this.institutions = institutions;
	}

	public List<AppUser> getWorkers() {
		return workers;
	}

	public void setWorkers(List<AppUser> workers) {
		this.workers = workers;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Set<AppUser> getWorkersFromInstitution() {
		return workersFromInstitution;
	}

	public void setWorkersFromInstitution(Set<AppUser> workersFromInstitution) {
		this.workersFromInstitution = workersFromInstitution;
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
