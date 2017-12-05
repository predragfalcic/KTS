package com.projekat.kts.dto;

import java.util.List;
import java.util.Set;

import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Building;
import com.projekat.kts.model.Failure;
import com.projekat.kts.model.Institution;

public class WorkerProfileDTO {

	private AppUser worker;
	private Set<Building> buildings;
	private List<Failure> failures;
	private Institution institution;
	private List<Failure> workerFailures;
	
	public WorkerProfileDTO(){}

	public AppUser getWorker() {
		return worker;
	}

	public void setWorker(AppUser worker) {
		this.worker = worker;
	}

	public Set<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(Set<Building> buildings) {
		this.buildings = buildings;
	}

	public List<Failure> getFailures() {
		return failures;
	}

	public void setFailures(List<Failure> failures) {
		this.failures = failures;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public List<Failure> getWorkerFailures() {
		return workerFailures;
	}

	public void setWorkerFailures(List<Failure> workerFailures) {
		this.workerFailures = workerFailures;
	}
}
