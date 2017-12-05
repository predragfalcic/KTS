package com.projekat.kts.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.kts.dto.FailureDateDTO;
import com.projekat.kts.dto.WorkerProfileDTO;
import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Building;
import com.projekat.kts.model.Failure;
import com.projekat.kts.model.Institution;
import com.projekat.kts.repository.AppUserRepository;
import com.projekat.kts.services.BuildingService;
import com.projekat.kts.services.FailureService;

@RestController
@RequestMapping(value = "/api")
public class RadnikController {

	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private FailureService failureService;
	
	@Autowired
	private BuildingService buildingService;
	
	/**
	 * Web service for getting the worker data
	 * 
	 * @return WorkerProfileDTO
	 */
	@PreAuthorize("hasRole('ROLE_WORKER')")
	@RequestMapping(value = "/worker/{id}", method = RequestMethod.GET)
	public ResponseEntity<WorkerProfileDTO> profil(@PathVariable Long id) {
		WorkerProfileDTO tb = new WorkerProfileDTO();
		AppUser worker = appUserRepository.findOne(id);
		
		if(worker == null){
			return new ResponseEntity<WorkerProfileDTO>(HttpStatus.NO_CONTENT);
		}
		Institution institution = worker.getInstitution(); // institucija u kojoj radi
		
		tb.setWorker(worker);
		tb.setInstitution(institution);
		tb.setWorkerFailures(failureService.findByWorker(worker));
		return new ResponseEntity<WorkerProfileDTO>(tb, HttpStatus.OK);
	}
	
	/**
	 * Web service for getting the failures of specific building
	 * 
	 * id je id zgrade za koju vracamo sve kvarove
	 * 
	 * @return WorkerProfileDTO
	 */
	@PreAuthorize("hasRole('ROLE_WORKER')")
	@RequestMapping(value = "/worker/failures/{id}", method = RequestMethod.GET)
	public ResponseEntity<WorkerProfileDTO> failures(@PathVariable Long id) {
		WorkerProfileDTO tb = new WorkerProfileDTO();
		
		Building building = buildingService.findOneById(id);
		
		if(building == null){
			return new ResponseEntity<WorkerProfileDTO>(HttpStatus.NO_CONTENT);
		}
		
		tb.setFailures(failureService.findByBuilding(building));
		return new ResponseEntity<WorkerProfileDTO>(tb, HttpStatus.OK);
	}
	
	/**
	 * Web service za prihvatanje posla od strane radnika
	 * 
	 * @return WorkerProfileDTO
	 */
	@PreAuthorize("hasRole('ROLE_WORKER')")
	@RequestMapping(value = "/worker/{id}/{failureId}", method = RequestMethod.PUT)
	public ResponseEntity<WorkerProfileDTO> prihvati(@PathVariable Long id, @PathVariable Long failureId) {
		WorkerProfileDTO tb = new WorkerProfileDTO();
		
		AppUser worker = appUserRepository.findOne(id);
		Failure failure = failureService.findOneById(failureId);
		
		if(worker == null || failure == null){
			return new ResponseEntity<WorkerProfileDTO>(HttpStatus.NO_CONTENT);
		}
		Institution institution = worker.getInstitution(); // institucija u kojoj radi
		
		// postavi kvaru ko radi na njemu
		failure.setWorker(worker);
		failure.setHasWorker(true);
		failureService.save(failure);
		
		// Postavi radniku kvar na kome radi
		worker.getFailures().add(failure);
		appUserRepository.save(worker);
		
		tb.setWorker(worker);
		tb.setInstitution(institution);
		tb.setFailures(failureService.findByBuilding(failure.getBuilding()));
		tb.setWorkerFailures(failureService.findByWorker(worker));
		return new ResponseEntity<WorkerProfileDTO>(tb, HttpStatus.OK);
	}
	
	/**
	 * Web service za prihvatanje posla od strane radnika
	 * 
	 * @return WorkerProfileDTO
	 */
	@PreAuthorize("hasRole('ROLE_WORKER')")
	@RequestMapping(value = "/worker/decline/{id}/{failureId}", method = RequestMethod.PUT)
	public ResponseEntity<WorkerProfileDTO> otkazi(@PathVariable Long id, @PathVariable Long failureId) {
		WorkerProfileDTO tb = new WorkerProfileDTO();
		
		AppUser worker = appUserRepository.findOne(id);
		Failure failure = failureService.findOneById(failureId);
		
		if(worker == null || failure == null){
			return new ResponseEntity<WorkerProfileDTO>(HttpStatus.NO_CONTENT);
		}
		Institution institution = worker.getInstitution(); // institucija u kojoj radi
		
		// postavi kvaru ko radi na njemu
		failure.setWorker(null);
		failure.setHasWorker(false);
		failureService.save(failure);
		
		// Postavi radniku kvar na kome radi
		worker.getFailures().remove(failure);
		appUserRepository.save(worker);
		
		tb.setWorker(worker);
		tb.setInstitution(institution);
		tb.setFailures(failureService.findByBuilding(failure.getBuilding()));
		tb.setWorkerFailures(failureService.findByWorker(worker));
		return new ResponseEntity<WorkerProfileDTO>(tb, HttpStatus.OK);
	}
	
	/**
	 * Web service za zakazivanje termina popravke kvara
	 * 
	 * @return WorkerProfileDTO
	 */
	@PreAuthorize("hasRole('ROLE_WORKER')")
	@RequestMapping(value = "/worker/zakazi/", method = RequestMethod.PUT)
	public ResponseEntity<WorkerProfileDTO> zakazi(@RequestBody FailureDateDTO dto) {
		WorkerProfileDTO tb = new WorkerProfileDTO();

		AppUser worker = appUserRepository.findOne(dto.getFailure().getWorker().getId());
		Failure failure = failureService.findOneById(dto.getFailure().getId());
		
		if(worker == null || failure == null){
			return new ResponseEntity<WorkerProfileDTO>(HttpStatus.NO_CONTENT);
		}
		Institution institution = worker.getInstitution(); // institucija u kojoj radi
		
		// postavi datum termina popravke kvara
		failure.setDateZakazano(new Date(dto.getDate()));
		failure.setHasZakazano(true);
		failureService.save(failure);
		
		tb.setWorker(worker);
		tb.setInstitution(institution);
		tb.setFailures(failureService.findByBuilding(failure.getBuilding()));
		tb.setWorkerFailures(failureService.findByWorker(worker));
		return new ResponseEntity<WorkerProfileDTO>(tb, HttpStatus.OK);
	}
	
	/**
	 * Web service za otkazivanje termina popravke kvara
	 * 
	 * @return WorkerProfileDTO
	 */
	@PreAuthorize("hasRole('ROLE_WORKER')")
	@RequestMapping(value = "/worker/otkazi/", method = RequestMethod.PUT)
	public ResponseEntity<WorkerProfileDTO> otkazi(@RequestBody Failure f) {
		WorkerProfileDTO tb = new WorkerProfileDTO();
		
		AppUser worker = appUserRepository.findOne(f.getWorker().getId());
		Failure failure = failureService.findOneById(f.getId());
		
		if(worker == null || failure == null){
			return new ResponseEntity<WorkerProfileDTO>(HttpStatus.NO_CONTENT);
		}
		Institution institution = worker.getInstitution(); // institucija u kojoj radi
		
		// postavi datum termina popravke kvara
		failure.setDateZakazano(null);
		failure.setHasZakazano(false);
		failureService.save(failure);
		
		tb.setWorker(worker);
		tb.setInstitution(institution);
		tb.setFailures(failureService.findByBuilding(failure.getBuilding()));
		tb.setWorkerFailures(failureService.findByWorker(worker));
		return new ResponseEntity<WorkerProfileDTO>(tb, HttpStatus.OK);
	}
	
	/**
	 * Web service postavljamo kvar kao zavrsen posao
	 * 
	 * @return WorkerProfileDTO
	 */
	@PreAuthorize("hasRole('ROLE_WORKER')")
	@RequestMapping(value = "/worker/zavrsen/", method = RequestMethod.PUT)
	public ResponseEntity<WorkerProfileDTO> zavrsenPosao(@RequestBody Failure f) {
		WorkerProfileDTO tb = new WorkerProfileDTO();
		
		AppUser worker = appUserRepository.findOne(f.getWorker().getId());
		Failure failure = failureService.findOneById(f.getId());
		
		if(worker == null || failure == null){
			return new ResponseEntity<WorkerProfileDTO>(HttpStatus.NO_CONTENT);
		}
		Institution institution = worker.getInstitution(); // institucija u kojoj radi
		
		// postavi datum termina popravke kvara
		failure.setSolved(true);
		failure.setDateSolved(new Date());
		failureService.save(failure);
		
		tb.setWorker(worker);
		tb.setInstitution(institution);
		tb.setFailures(failureService.findByBuilding(failure.getBuilding()));
		tb.setWorkerFailures(failureService.findByWorker(worker));
		return new ResponseEntity<WorkerProfileDTO>(tb, HttpStatus.OK);
	}
}
