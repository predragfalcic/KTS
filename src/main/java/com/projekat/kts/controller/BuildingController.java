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

import com.projekat.kts.dto.ApartmentsBuildingDTO;
import com.projekat.kts.model.Apartmen;
import com.projekat.kts.model.Building;
import com.projekat.kts.model.Institution;
import com.projekat.kts.services.ApartmenService;
import com.projekat.kts.services.BuildingService;
import com.projekat.kts.services.InstitutionService;

@RestController
@RequestMapping(value = "/api")
public class BuildingController {

	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private InstitutionService institutionService;
	
	@Autowired
	private ApartmenService apartmenService;
	
	/**
	 * Web service for getting all the buildings
	 * and apartmens that have no building yet set from the database
	 * 
	 * @return list of all Buildings
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buildings", method = RequestMethod.GET)
	public ApartmentsBuildingDTO buildings() {
		ApartmentsBuildingDTO tb = new ApartmentsBuildingDTO();
		tb.setBuildings(buildingService.findAll());
		tb.setApartmens(apartmenService.findByHasBuilding(false));
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
			for (Apartmen a : building.getApartments()) {
				a.setApartmenBuilding(null);
				a.setHasApartmentBuilding(false);
				apartmenService.save(a);
			}
			for (Institution in : building.getInstitutions()) {
				in.getBuildings().remove(building);
				institutionService.save(in);
			}
			building.setApartments(null);
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
	public ApartmentsBuildingDTO updateBuilding(@RequestBody Building building) {
		// Check if the building already exists with that name
		// And that that building is not the one that is being edited
		Building foundBuilding = buildingService.findOneByName(building.getName());
		if (foundBuilding != null
				&& foundBuilding.getId() != building.getId()) {
			throw new RuntimeException("Name already exist");
		}
		
		ApartmentsBuildingDTO tb = new ApartmentsBuildingDTO();
		tb.setBuildings(buildingService.findAll());
		tb.setApartmens(apartmenService.findAll());
		tb.setApartmensFromBuilding(building.getApartments());
		
		if(building.getApartments() != null){
			for (Apartmen a : building.getApartments()) {
				a.setApartmenBuilding(building);
				a.setHasApartmentBuilding(true);
				apartmenService.save(a);
			}
		}
		
		buildingService.save(building);
		System.out.println(building);
		
		return tb;
	}
	
	/**
	 * Method for deleting apartment from building
	 * 
	 * @param Long buildingId, Long apartmentId
	 * @return TenatsApartmenDTO
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buildings/delete/stan/{buildingId}/{apartmentId}", method = RequestMethod.PUT)
	public ApartmentsBuildingDTO deleteStanFromBuilding(@PathVariable Long buildingId, @PathVariable Long apartmentId) {
		
		Building foundBuilding = buildingService.findOneById(buildingId);
		
		// Find the apartment to be deleted from building
		Apartmen apartmen = apartmenService.findOneById(apartmentId);
		
		apartmen.setApartmenBuilding(null);
		apartmen.setHasApartmentBuilding(false);

		apartmenService.save(apartmen);
		
		foundBuilding.getApartments().remove(apartmen);
		
		buildingService.save(foundBuilding);
		
		ApartmentsBuildingDTO tb = new ApartmentsBuildingDTO();
		tb.setBuildings(buildingService.findAll());
		tb.setApartmens(apartmenService.findByHasBuilding(false));
		tb.setApartmensFromBuilding(foundBuilding.getApartments());
		tb.setBuilding(foundBuilding);
		return tb;
	}
	
	/**
	 * Method for adding apartment to building
	 * 
	 * @param Long buildingId, Long apartmentId
	 * @return TenatsApartmenDTO
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buildings/add/stan/{buildingId}/{apartmentId}", method = RequestMethod.PUT)
	public ApartmentsBuildingDTO AddStanToBuilding(@PathVariable Long buildingId, @PathVariable Long apartmentId) {
		
		Building foundBuilding = buildingService.findOneById(buildingId);
		
		// Find the apartment to be added to building
		Apartmen apartmen = apartmenService.findOneById(apartmentId);
		
		apartmen.setApartmenBuilding(foundBuilding);
		apartmen.setHasApartmentBuilding(true);
		apartmenService.save(apartmen);
		
		foundBuilding.getApartments().add(apartmen);
		
		buildingService.save(foundBuilding);
		
		ApartmentsBuildingDTO tb = new ApartmentsBuildingDTO();
		tb.setBuildings(buildingService.findAll());
		tb.setApartmens(apartmenService.findByHasBuilding(false));
		tb.setApartmensFromBuilding(foundBuilding.getApartments());
		tb.setBuilding(foundBuilding);
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
	public ApartmentsBuildingDTO deleteInstitutionFromBuilding(@PathVariable Long buildingId, @PathVariable Long institutionId) {
		// Check if the building already exists with that name
		// And that that building is not the one that is being edited
		Building foundBuilding = buildingService.findOneById(buildingId);
		
		ApartmentsBuildingDTO tb = new ApartmentsBuildingDTO();
		tb.setBuildings(buildingService.findAll());
		tb.setApartmens(apartmenService.findAll());
		tb.setApartmensFromBuilding(foundBuilding.getApartments());
		
		for (Apartmen a : foundBuilding.getApartments()) {
			a.setApartmenBuilding(foundBuilding);
			a.setHasApartmentBuilding(true);
			apartmenService.save(a);
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
