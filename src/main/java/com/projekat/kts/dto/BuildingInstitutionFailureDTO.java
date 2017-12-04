package com.projekat.kts.dto;

import java.util.List;

import com.projekat.kts.model.Building;
import com.projekat.kts.model.Failure;
import com.projekat.kts.model.Institution;

public class BuildingInstitutionFailureDTO {

	private List<Failure> failures;
	private Building building;
	private Institution institution;
	private List<Institution> institutions;
	
	public BuildingInstitutionFailureDTO(){}

	public List<Failure> getFailures() {
		return failures;
	}

	public void setFailures(List<Failure> failures) {
		this.failures = failures;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public List<Institution> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(List<Institution> institutions) {
		this.institutions = institutions;
	}
	
}
