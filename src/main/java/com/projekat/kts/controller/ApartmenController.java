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

import com.projekat.kts.dto.TenatsApartmenDTO;
import com.projekat.kts.model.Apartmen;
import com.projekat.kts.model.AppUser;
import com.projekat.kts.repository.AppUserRepository;
import com.projekat.kts.services.ApartmenService;
import com.projekat.kts.services.BuildingService;

@RestController
@RequestMapping(value = "/api")
public class ApartmenController {

	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private ApartmenService apartmenService;
	
	@Autowired
	private BuildingService buildingService;
	
	/**
	 * Web service for getting all the apartmens
	 * and tenats that have no apartmens yet set from the database
	 * 
	 * @return list of all Apartmens
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/apartmens", method = RequestMethod.GET)
	public TenatsApartmenDTO apartmens() {
		TenatsApartmenDTO tb = new TenatsApartmenDTO();
		tb.setApartmens(apartmenService.findAll());
		tb.setTenats(appUserRepository.findByRolesAndHasBuilding("STANAR", false));
		tb.setBuildings(buildingService.findAll());
		return tb;
	}
	
	/**
	 * Web service for getting a apartmen by it's ID
	 * 
	 * @param id
	 *            
	 * @return Apartmen
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STANAR')")
	@RequestMapping(value = "/apartmens/{id}", method = RequestMethod.GET)
	public ResponseEntity<Apartmen> apartmenById(@PathVariable Long id) {
		Apartmen apartmen = apartmenService.findOneById(id);
		if (apartmen == null) {
			return new ResponseEntity<Apartmen>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Apartmen>(apartmen, HttpStatus.OK);
		}
	}
	
	/**
	 * Method for deleting a apartmen by its ID
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/apartmens/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Apartmen> deleteApartmen(@PathVariable Long id) {
		Apartmen apartmen = apartmenService.findOneById(id);
		if (apartmen == null) {
			return new ResponseEntity<Apartmen>(HttpStatus.NO_CONTENT);
		} else {
			for (AppUser a : apartmen.getApartmen_tenats()) {
				a.setApartmen(null);
				a.setHasBuilding(false);
				a.setOwner(false);
				appUserRepository.save(a);
			}
			apartmen.setApartmen_tenats(null);
			apartmen.setApartmenBuilding(null);
			apartmenService.delete(apartmen);
			return new ResponseEntity<Apartmen>(apartmen, HttpStatus.OK);
		}
	}

	/**
	 * Method for adding a apartmen
	 * 
	 * @param Apartment
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/apartmens", method = RequestMethod.POST)
	public ResponseEntity<Apartmen> createApartmen(@RequestBody Apartmen apartmen) {
		
		System.out.println(apartmen.toString());
		
		Apartmen foundApartmen = apartmenService.findOneByName(apartmen.getName());
		if (foundApartmen != null) {
			throw new RuntimeException("Name already exist");
		}
		apartmen.setNumberOfTenats(0);
		Apartmen newApartmen = apartmenService.save(apartmen);
		
		return new ResponseEntity<Apartmen>(newApartmen, HttpStatus.CREATED);
	}

	/**
	 * Method for editing a building's details
	 * 
	 * @param Building
	 * @return modified building
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/apartmens", method = RequestMethod.PUT)
	public TenatsApartmenDTO updateApartmen(@RequestBody Apartmen apartmen) {
		// Check if the apartmen already exists with that name
		// And that that apartmen is not the one that is being edited
		Apartmen foundApartmen = apartmenService.findOneByName(apartmen.getName());
		if (foundApartmen != null
				&& foundApartmen.getId() != apartmen.getId()) {
			throw new RuntimeException("Name already exist");
		}
		
		TenatsApartmenDTO tb = new TenatsApartmenDTO();
		tb.setApartmens(apartmenService.findAll());
		tb.setTenats(appUserRepository.findByRolesAndHasBuilding("STANAR", false));
		tb.setTenatsFromApartmen(apartmen.getApartmen_tenats());
		
		for (AppUser tenat : apartmen.getApartmen_tenats()) {
			tenat.setHasBuilding(true);
			tenat.setApartmen(apartmen);
			appUserRepository.save(tenat);
		}
		apartmen.setNumberOfTenats(apartmen.getApartmen_tenats().size());
		apartmenService.save(apartmen);
		System.out.println(apartmen);
		
		return tb;
	}
	
	/**
	 * Method for deleting tenat from apartmen
	 * 
	 * @param Long apartmanId, Long stanarId
	 * @return TenatsApartmenDTO
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/apartmens/delete/tenat/{apartmanId}/{stanarId}", method = RequestMethod.PUT)
	public TenatsApartmenDTO deleteStanarFromApartman(@PathVariable Long apartmanId, @PathVariable Long stanarId) {
		
		Apartmen foundApartmen = apartmenService.findOneById(apartmanId);
		
		// Find the tenat to be deleted from apartman
		AppUser tenat = appUserRepository.findOne(stanarId);
		
		tenat.setApartmen(null);
		tenat.setHasBuilding(false);
		
		// Check if the tenat is the owner of the apartment
		if(tenat.isOwner()){
			foundApartmen.setHasOwner(false); // Set that the apartmen now has no owner
			tenat.setOwner(false); // Set the user to now be owner of any apartmen
		}
		appUserRepository.save(tenat);
		
		foundApartmen.getApartmen_tenats().remove(tenat);
		foundApartmen.setNumberOfTenats(foundApartmen.getApartmen_tenats().size());
		
		apartmenService.save(foundApartmen);
		
		TenatsApartmenDTO tb = new TenatsApartmenDTO();
		tb.setApartmens(apartmenService.findAll());
		tb.setTenats(appUserRepository.findByRolesAndHasBuilding("STANAR", false));
		tb.setTenatsFromApartmen(foundApartmen.getApartmen_tenats());
		tb.setApartmen(foundApartmen);
		return tb;
	}
	
	/**
	 * Method for adding tenat to apartmen
	 * 
	 * @param Long apartmanId, Long stanarId
	 * @return TenatsApartmenDTO
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/apartmens/add/tenat/{apartmanId}/{stanarId}", method = RequestMethod.PUT)
	public TenatsApartmenDTO AddStanarToApartman(@PathVariable Long apartmanId, @PathVariable Long stanarId) {
		
		Apartmen foundApartmen = apartmenService.findOneById(apartmanId);
		System.out.println(foundApartmen.toString());
		// Find the tenat to be added to apartman
		AppUser tenat = appUserRepository.findOne(stanarId);
		
		tenat.setApartmen(foundApartmen);
		tenat.setHasBuilding(true);
		tenat.setOwner(false);
		appUserRepository.save(tenat);
		
		foundApartmen.getApartmen_tenats().add(tenat);
		foundApartmen.setNumberOfTenats(foundApartmen.getApartmen_tenats().size());
		
		apartmenService.save(foundApartmen);
		
		TenatsApartmenDTO tb = new TenatsApartmenDTO();
		tb.setApartmens(apartmenService.findAll());
		tb.setTenats(appUserRepository.findByRolesAndHasBuilding("STANAR", false));
		tb.setTenatsFromApartmen(foundApartmen.getApartmen_tenats());
		tb.setApartmen(foundApartmen);
		return tb;
	}
	
	/**
	 * Method for adding owner to apartmen
	 * 
	 * @param Long apartmanId, Long stanarId
	 * @return TenatsApartmenDTO
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/apartmens/add/owner/{apartmanId}/{stanarId}", method = RequestMethod.PUT)
	public TenatsApartmenDTO AddOwnerToApartman(@PathVariable Long apartmanId, @PathVariable Long stanarId) {
		
		Apartmen foundApartmen = apartmenService.findOneById(apartmanId);
//		System.out.println(foundApartmen.toString());
		// Find the tenat to be set for owner
		AppUser tenat = appUserRepository.findOne(stanarId);
		
//		tenat.setApartmen(foundApartmen);
//		tenat.setHasBuilding(true);
		tenat.setOwner(true);
		appUserRepository.save(tenat);
		
		foundApartmen.setHasOwner(true);
		foundApartmen.setOwner(tenat.getName());
		apartmenService.save(foundApartmen);
		
		TenatsApartmenDTO tb = new TenatsApartmenDTO();
		tb.setApartmens(apartmenService.findAll());
		tb.setTenats(appUserRepository.findByRolesAndHasBuilding("STANAR", false));
		tb.setTenatsFromApartmen(foundApartmen.getApartmen_tenats());
		tb.setApartmen(foundApartmen);
		return tb;
	}
	
	/**
	 * Method for adding owner to apartmen
	 * 
	 * @param Long apartmanId, Long stanarId
	 * @return TenatsApartmenDTO
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/apartmens/delete/owner/{apartmanId}/{stanarId}", method = RequestMethod.PUT)
	public TenatsApartmenDTO DeleteOwnerToApartman(@PathVariable Long apartmanId, @PathVariable Long stanarId) {
		
		Apartmen foundApartmen = apartmenService.findOneById(apartmanId);
//		System.out.println(foundApartmen.toString());
		// Find the tenat to be set for owner
		AppUser tenat = appUserRepository.findOne(stanarId);
		
//		tenat.setApartmen(foundApartmen);
//		tenat.setHasBuilding(true);
		tenat.setOwner(false);
		appUserRepository.save(tenat);
		
		foundApartmen.setHasOwner(false);
		foundApartmen.setOwner(null);
		apartmenService.save(foundApartmen);
		
		TenatsApartmenDTO tb = new TenatsApartmenDTO();
		tb.setApartmens(apartmenService.findAll());
		tb.setTenats(appUserRepository.findByRolesAndHasBuilding("STANAR", false));
		tb.setTenatsFromApartmen(foundApartmen.getApartmen_tenats());
		tb.setApartmen(foundApartmen);
		return tb;
	}
}
