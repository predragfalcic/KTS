package com.projekat.kts.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.kts.dto.SednicaDTO;
import com.projekat.kts.dto.SednicaDetailsDTO;
import com.projekat.kts.dto.StavkaDTO;
import com.projekat.kts.dto.ZapisnikDTO;
import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Sednica;
import com.projekat.kts.model.Stavka;
import com.projekat.kts.model.Zapisnik;
import com.projekat.kts.repository.AppUserRepository;
import com.projekat.kts.services.SednicaService;
import com.projekat.kts.services.StavkaService;
import com.projekat.kts.services.ZapisnikService;

@RestController
@RequestMapping(value = "/api")
public class SednicaController {
	
	@Autowired
	private SednicaService sednicaService;

	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private ZapisnikService zapisnikService;
	
	@Autowired
	private StavkaService stavkaService;
	
	/**
	 * Web service for getting all the sednica
	 * 
	 * id je id korisnika
	 * 
	 * @return list of all Sednica
	 */
	@PreAuthorize("hasRole('ROLE_STANAR')")
	@RequestMapping(value = "/sednica/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Sednica>> sednica(@PathVariable Long id) {
		AppUser tenat = appUserRepository.findOne(id);
		if(tenat == null){
			return new ResponseEntity<List<Sednica>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Sednica>>(sednicaService.findByBuilding(tenat.getApartmen().getApartmenBuilding()), HttpStatus.OK);
	}
	
	/**
	 * Web service for creating sednica
	 * 
	 * @return SednicaDTO
	 */
	@PreAuthorize("hasRole('ROLE_STANAR')")
	@RequestMapping(value = "/sednica", method = RequestMethod.POST)
	public ResponseEntity<List<Sednica>> addSednica(@RequestBody SednicaDTO dto) {
		Sednica sednica = new Sednica();
		AppUser creator = appUserRepository.findOne(dto.getCreator().getId());
		
		if(creator == null){
			return new ResponseEntity<List<Sednica>>(HttpStatus.NO_CONTENT);
		}
		System.out.println(dto.getDateScheduled().getTime());
		sednica.setDateCreated(new Date());
		sednica.setDateScheduled(new Date(dto.getDateScheduled().getTime()));
		sednica.setCreator(creator);
		sednica.setActive(true);
		sednica.setBuilding(creator.getApartmen().getApartmenBuilding()); // Kojoj zgradi pripada sednica
		
		System.out.println(sednica.toString());
		
		sednicaService.save(sednica);
		
		return new ResponseEntity<List<Sednica>>(sednicaService.findByBuilding(creator.getApartmen().getApartmenBuilding()), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id - id sednice
	 * @param tenatId - id korisnika koji zeli da pristupi stranici
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_STANAR')")
	@RequestMapping(value = "/sednica_details/{id}/{tenatId}", method = RequestMethod.GET)
	public ResponseEntity<SednicaDetailsDTO> sednicaDetails(@PathVariable Long id, @PathVariable Long tenatId) {
		Sednica sednica = sednicaService.findById(id);
		AppUser tenat = appUserRepository.findOne(tenatId);
		
		if(sednica == null){
			return new ResponseEntity<SednicaDetailsDTO>(HttpStatus.NO_CONTENT);
		}
		
		SednicaDetailsDTO dto = new SednicaDetailsDTO();
		dto.setStavke(stavkaService.findBySednica(sednica));
		dto.setZapisnici(zapisnikService.findBySednica(sednica));
		dto.setTenat(tenat);
		
		return new ResponseEntity<SednicaDetailsDTO>(dto, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_STANAR')")
	@RequestMapping(value = "/sednica/stavka", method = RequestMethod.POST)
	public ResponseEntity<SednicaDetailsDTO> addStavka(@RequestBody StavkaDTO stavkaDTO) {
		Sednica sednica = sednicaService.findById(stavkaDTO.getSednicaId());
		AppUser tenat = appUserRepository.findOne(stavkaDTO.getTenatId());
		if(sednica == null || tenat == null){
			return new ResponseEntity<SednicaDetailsDTO>(HttpStatus.NO_CONTENT);
		}
		
		Stavka stavka = stavkaDTO.getStavka();
		stavka.setDateCreated(new Date());
		stavka.setSednica(sednica);
		stavka.setTenat(tenat);
		stavkaService.save(stavka);
		
		sednica.getStavke().add(stavka);
		sednicaService.save(sednica);
		
		SednicaDetailsDTO dto = new SednicaDetailsDTO();
		dto.setStavke(stavkaService.findBySednica(sednica));
		
		return new ResponseEntity<SednicaDetailsDTO>(dto, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_STANAR')")
	@RequestMapping(value = "/sednica/zapisnik/", method = RequestMethod.POST)
	public ResponseEntity<SednicaDetailsDTO> addZapisnik(@RequestBody ZapisnikDTO zapisnikDTO) {
		Sednica sednica = sednicaService.findById(zapisnikDTO.getSednicaId());
		AppUser tenat = appUserRepository.findOne(zapisnikDTO.getTenatId());
		if(sednica == null || tenat == null){
			return new ResponseEntity<SednicaDetailsDTO>(HttpStatus.NO_CONTENT);
		}
		Zapisnik zapisnik = zapisnikDTO.getZapisnik();
		zapisnik.setCreator(tenat);
		zapisnik.setDateCreated(new Date());
		zapisnik.setSednica(sednica);
		zapisnikService.save(zapisnik);
		
		sednica.getZapisnici().clear();
		sednica.getZapisnici().add(zapisnik);
		sednica.setActive(false);
		sednicaService.save(sednica);
		
		SednicaDetailsDTO dto = new SednicaDetailsDTO();
		dto.setZapisnici(zapisnikService.findBySednica(sednica));
		dto.setSednica(sednica);
		
		return new ResponseEntity<SednicaDetailsDTO>(dto, HttpStatus.OK);
	}
}
