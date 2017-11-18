package com.projekat.kts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.kts.dto.TenatsBuildingDTO;
import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Building;
import com.projekat.kts.model.Institution;
import com.projekat.kts.repository.AppUserRepository;
import com.projekat.kts.services.BuildingService;
import com.projekat.kts.services.InstitutionService;

@RestController
@RequestMapping(value = "/api")
public class BuildingController {

	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private InstitutionService institutionService;
	
	/**
	 * Web service for getting all the buildings
	 * and tenats that have no building yet set from the database
	 * 
	 * @return list of all Buildings
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buildings", method = RequestMethod.GET)
	public TenatsBuildingDTO buildings() {
		TenatsBuildingDTO tb = new TenatsBuildingDTO();
		tb.setBuildings(buildingService.findAll());
		tb.setTenats(appUserRepository.findByRolesAndHasBuilding("STANAR", false));
		return tb;
	}
	
	/**
	 * Web service for getting a building by it's ID
	 * 
	 * @param id
	 *            
	 * @return Building
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STANAR')")
	@RequestMapping(value = "/buildings/{id}", method = RequestMethod.GET)
	public ResponseEntity<Building> buildingById(@PathVariable Long id) {
		Building building = buildingService.findOneById(id);
		if (building == null) {
			return new ResponseEntity<Building>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Building>(building, HttpStatus.OK);
		}
	}
	
	/**
	 * Method for deleting a building by its ID
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buildings/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Building> deleteBuilding(@PathVariable Long id) {
		Building building = buildingService.findOneById(id);
		if (building == null) {
			return new ResponseEntity<Building>(HttpStatus.NO_CONTENT);
		} else {
			for (AppUser user : building.getTenats()) {
				user.setBuilding(null);
				user.setHasBuilding(false);
				appUserRepository.save(user);
			}
			for (Institution in : building.getInstitutions()) {
				in.getBuildings().remove(building);
				institutionService.save(in);
			}
			building.setTenats(null);
			buildingService.delete(building);
			return new ResponseEntity<Building>(building, HttpStatus.OK);
		}
	}

	/**
	 * Method for adding a building
	 * 
	 * @param Building
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buildings", method = RequestMethod.POST)
	public ResponseEntity<Building> createBuilding(@RequestBody Building building) {

		Building foundBuilding = buildingService.findOneByName(building.getName());
		if (foundBuilding != null) {
			throw new RuntimeException("Name already exist");
		}
		
		Building newBuilding = buildingService.save(building);

		for (AppUser tenat : building.getTenats()) {
			tenat.setBuilding(newBuilding);
			tenat.setHasBuilding(true);
			appUserRepository.save(tenat);
		}
		
		return new ResponseEntity<Building>(newBuilding, HttpStatus.CREATED);
	}

	/**
	 * Method for editing a building's details
	 * 
	 * @param Building
	 * @return modified building
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buildings", method = RequestMethod.PUT)
	public TenatsBuildingDTO updateBuilding(@RequestBody Building building) {
		// Check if the building already exists with that name
		// And that that building is not the one that is being edited
		Building foundBuilding = buildingService.findOneByName(building.getName());
		if (foundBuilding != null
				&& foundBuilding.getId() != building.getId()) {
			throw new RuntimeException("Name already exist");
		}
		
		TenatsBuildingDTO tb = new TenatsBuildingDTO();
		tb.setBuildings(buildingService.findAll());
		tb.setTenats(appUserRepository.findByRolesAndHasBuilding("STANAR", false));
		tb.setTenatsFromBuilding(building.getTenats());
		
		for (AppUser tenat : building.getTenats()) {
			tenat.setBuilding(building);
			tenat.setHasBuilding(true);
			appUserRepository.save(tenat);
		}
		buildingService.save(building);
		System.out.println(building);
		
		return tb;
	}
	
	/**
	 * Method for deleting institution from building
	 * 
	 * @param Long buildingId, Long institutionId
	 * @return TenatsBuildingDTO
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buildings/{buildingId}/{institutionId}", method = RequestMethod.PUT)
	public TenatsBuildingDTO deleteInstitutionFromBuilding(@PathVariable Long buildingId, @PathVariable Long institutionId) {
		// Check if the building already exists with that name
		// And that that building is not the one that is being edited
		Building foundBuilding = buildingService.findOneById(buildingId);
		
		TenatsBuildingDTO tb = new TenatsBuildingDTO();
		tb.setBuildings(buildingService.findAll());
		tb.setTenats(appUserRepository.findByRolesAndHasBuilding("STANAR", false));
		tb.setTenatsFromBuilding(foundBuilding.getTenats());
		
		for (AppUser tenat : foundBuilding.getTenats()) {
			tenat.setBuilding(foundBuilding);
			tenat.setHasBuilding(true);
			appUserRepository.save(tenat);
		}
		Institution in = institutionService.findOneById(institutionId);
		// Delete the institution from the building
		// because the  institution is the owner of this relationship
		// we have to delete the building from the institution
		in.getBuildings().remove(foundBuilding);
		// Delete the institution from the list of 
		// institutioons in the building
		foundBuilding.getInstitutions().remove(in);
		
		// Save the changes
		buildingService.save(foundBuilding);
		institutionService.save(in);
//		System.out.println(foundBuilding);
		
		return tb;
	}
}
