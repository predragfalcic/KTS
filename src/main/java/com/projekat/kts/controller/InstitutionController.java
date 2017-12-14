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

import com.projekat.kts.dto.WorkerInstitutionDTO;
import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Institution;
import com.projekat.kts.repository.AppUserRepository;
import com.projekat.kts.services.BuildingService;
import com.projekat.kts.services.InstitutionService;

@RestController
@RequestMapping(value = "/api")
public class InstitutionController {

	@Autowired
	private InstitutionService institutionService;
	
	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	/**
	 * Web service for getting all the institutions
	 * and buildings from the database
	 * 
	 * @return InstitutionBuildings
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/institutions", method = RequestMethod.GET)
	public WorkerInstitutionDTO institutions() {
		WorkerInstitutionDTO ib = new WorkerInstitutionDTO();
		ib.setBuildings(buildingService.findAll());
		ib.setWorkers(appUserRepository.findByRolesAndHasInstitution("WORKER", false));
		ib.setInstitutions(institutionService.findAll());
		return ib;
	}
	
	/**
	 * Web service for getting a institution by it's ID
	 * 
	 * @param id
	 *            
	 * @return Institution
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STANAR')")
	@RequestMapping(value = "/institutions/{id}", method = RequestMethod.GET)
	public ResponseEntity<Institution> institutionById(@PathVariable Long id) {
		Institution institution = institutionService.findOneById(id);
		if (institution == null) {
			return new ResponseEntity<Institution>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Institution>(institution, HttpStatus.OK);
		}
	}
	
	/**
	 * Method for deleting a institution by its ID
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/institutions/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Institution> deleteInstitution(@PathVariable Long id) {
		Institution in = institutionService.findOneById(id);
		if (in == null) {
			return new ResponseEntity<Institution>(HttpStatus.NO_CONTENT);
		} else {
			for (AppUser a : in.getWorkers()) {
				a.setInstitution(null);
				a.setHasInstitution(false);
				appUserRepository.save(a);
			}
			in.setWorkers(null);
			institutionService.delete(in);
			return new ResponseEntity<Institution>(in, HttpStatus.OK);
		}
	}

	/**
	 * Method for adding a Institution
	 * 
	 * @param Institution
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/institutions", method = RequestMethod.POST)
	public ResponseEntity<Institution> createInstitution(@RequestBody Institution in) {

		Institution foundInstitution = institutionService.findOneByName(in.getName());
		if (foundInstitution != null) {
			throw new RuntimeException("Name already exist");
		}
		
		Institution newInstitutin = institutionService.save(in);
		
		return new ResponseEntity<Institution>(newInstitutin, HttpStatus.CREATED);
	}

	/**
	 * Method for editing a institution's details
	 * 
	 * @param Institution
	 * @return modified institution
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/institutions", method = RequestMethod.PUT)
	public WorkerInstitutionDTO updateInstitution(@RequestBody Institution in) {
		// Check if the institution already exists with that name
		// And that that institution is not the one that is being edited
		Institution foundInstitution = institutionService.findOneByName(in.getName());
		if (foundInstitution != null
				&& foundInstitution.getId() != in.getId()) {
			throw new RuntimeException("Name already exist");
		}
		WorkerInstitutionDTO ib = new WorkerInstitutionDTO();
		ib.setBuildings(buildingService.findAll());
		ib.setInstitutions(institutionService.findAll());
		ib.setInstitutionBuildings(in.getBuildings());
		
		if(in.getWorkers() != null){
			for (AppUser worker : in.getWorkers()) {
				worker.setHasInstitution(true);
				worker.setInstitution(in);
				appUserRepository.save(worker);
			}
		}
		
		ib.setWorkers(appUserRepository.findByRolesAndHasInstitution("WORKER", false));
		
		institutionService.save(in);
		
		return ib;
	}
	
	/**
	 * Method for deleting worker from institution
	 * 
	 * @param Long institutionId, Long workerId
	 * @return WorkerInstitutionDTO
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/institutions/delete/worker/{institutionId}/{workerId}", method = RequestMethod.PUT)
	public WorkerInstitutionDTO deleteWorkerFromInstitution(@PathVariable Long institutionId, @PathVariable Long workerId) {
		
		Institution foundInstitution = institutionService.findOneById(institutionId);
		
		// Find the worker to be deleted from the institution
		AppUser worker = appUserRepository.findOne(workerId);
		
		worker.setInstitution(null);
		worker.setHasInstitution(false);
		
		appUserRepository.save(worker);
		
		foundInstitution.getWorkers().remove(worker);
		
		institutionService.save(foundInstitution);
		
		WorkerInstitutionDTO tb = new WorkerInstitutionDTO();
		tb.setInstitutions(institutionService.findAll());
		tb.setWorkers(appUserRepository.findByRolesAndHasInstitution("WORKER", false));
		tb.setWorkersFromInstitution(foundInstitution.getWorkers());
		tb.setInstitution(foundInstitution);
		tb.setInstitutionBuildings(foundInstitution.getBuildings());
		return tb;
	}
	
	/**
	 * Method for adding worker to institution
	 * 
	 * @param Long institutionId, Long workerId
	 * @return WorkerInstitutionDTO
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/institutions/add/worker/{institutionId}/{workerId}", method = RequestMethod.PUT)
	public WorkerInstitutionDTO addWorkerFromInstitution(@PathVariable Long institutionId, @PathVariable Long workerId) {
		
		Institution foundInstitution = institutionService.findOneById(institutionId);
		
		// Find the worker to be deleted from the institution
		AppUser worker = appUserRepository.findOne(workerId);
		
		worker.setInstitution(foundInstitution);
		worker.setHasInstitution(true);
		appUserRepository.save(worker);
		
		foundInstitution.getWorkers().add(worker);
		
		institutionService.save(foundInstitution);
		
		WorkerInstitutionDTO tb = new WorkerInstitutionDTO();
		tb.setInstitutions(institutionService.findAll());
		tb.setWorkers(appUserRepository.findByRolesAndHasInstitution("WORKER", false));
		tb.setWorkersFromInstitution(foundInstitution.getWorkers());
		tb.setInstitution(foundInstitution);
		tb.setInstitutionBuildings(foundInstitution.getBuildings());
		return tb;
	}
}
