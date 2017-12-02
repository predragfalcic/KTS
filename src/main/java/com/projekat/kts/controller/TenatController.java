package com.projekat.kts.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.kts.dto.TenatProfileDTO;
import com.projekat.kts.model.Apartmen;
import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Building;
import com.projekat.kts.repository.AppUserRepository;

@RestController
@RequestMapping(value = "api/")
public class TenatController {
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	/**
	 * Web service for getting the tenat data
	 * 
	 * @return TenatProfileDTO
	 */
	@PreAuthorize("hasRole('ROLE_STANAR')")
	@RequestMapping(value = "/profil/{id}", method = RequestMethod.GET)
	public ResponseEntity<TenatProfileDTO> profil(@PathVariable Long id) {
		TenatProfileDTO tb = new TenatProfileDTO();
		AppUser tenat = appUserRepository.findOne(id);
		if(tenat == null){
			return new ResponseEntity<TenatProfileDTO>(HttpStatus.NO_CONTENT);
		}
		tb.setTenat(tenat);
		tb.setApartmen(tenat.getApartmen());
		tb.setBuilding(tenat.getApartmen().getApartmenBuilding());
		return new ResponseEntity<TenatProfileDTO>(tb, HttpStatus.OK);
	}
	
	/**
	 * Web service for getting all the owners from building
	 * 
	 * Long id is the id of the user who is logged in currently
	 * @return TenatProfileDTO
	 */
	@PreAuthorize("hasRole('ROLE_STANAR')")
	@RequestMapping(value = "/profil/owners/{id}", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<AppUser>> allOwners(@PathVariable Long id) {
		AppUser tenat = appUserRepository.findOne(id);
		
		if(tenat == null){
			return new ResponseEntity<ArrayList<AppUser>>(HttpStatus.NO_CONTENT);
		}
		Building building = tenat.getApartmen().getApartmenBuilding();
		
		ArrayList<AppUser> owners = new ArrayList<>();
//		System.out.println(building.getApartments().size());
		for (Apartmen a : building.getApartments()) {
//			System.out.println(a.getName());
			for (AppUser au : a.getApartmen_tenats()) {
				if(au.isOwner() && tenat.getId() != au.getId()){
					owners.add(au);
//					System.out.println(au.getName());
				}
			}
		}
		
		return new ResponseEntity<ArrayList<AppUser>>(owners, HttpStatus.OK);
	}
	
	/**
	 * Web service for upgrading user's votes
	 * 
	 * Long idVoted je id korisnika za koga se glasalo da bude predsednik
	 * Long idHasVoted id korisnika koji je glasao
	 * @return TenatProfileDTO
	 */
	@PreAuthorize("hasRole('ROLE_STANAR')")
	@RequestMapping(value = "/profil/president/{idVoted}/{idHasVoted}", method = RequestMethod.PUT)
	public ResponseEntity<TenatProfileDTO> upgradeVotes(@PathVariable Long idVoted, @PathVariable Long idHasVoted) {
		AppUser tenatVoted = appUserRepository.findOne(idVoted);
		AppUser tenatHasVoted = appUserRepository.findOne(idHasVoted);
		if(tenatVoted == null || tenatHasVoted == null){
			return new ResponseEntity<TenatProfileDTO>(HttpStatus.NO_CONTENT);
		}
		
		tenatVoted.setVotes(tenatVoted.getVotes() + 1); // Korisnik za koga se glasalo
		tenatHasVoted.setVoted(true); // korisnik koji je glasao
		
		appUserRepository.save(tenatVoted);
		appUserRepository.save(tenatHasVoted);
		
		// Preuzimamo listu svih korisnika koji su vlasnici stanova u zgradi
		// i koji su glasali za nekog predsednika
		// Ukoliko je prazna, znaci da su svi korisnici glasali
		
		Building building = tenatHasVoted.getApartmen().getApartmenBuilding();
		
		// Podatak da li su svi vlasnici glasali
		boolean votingDone = true;
		
		// Svi korisnici u zgradi
		ArrayList<AppUser> tenats = getAllTenat(building);
		
		// Proveri da li su svi glasali
		for (AppUser au : tenats) {
			if(au.isOwner() && !au.isVoted()){
				votingDone = false;
			}
		}
		
		AppUser president = null;
		
		// Ukoliko je glasanje zavrseno biramo predsednika
		if(votingDone){
			building.setHasPresident(true); // Postavimo indikator da zgrada ima predsednika
			
			// Stanar sa najvise glasova
			president = getMostVotedTenat(tenats);
			
			president.setTenatsPresident(true);
			
			appUserRepository.save(president);
		}
		
		TenatProfileDTO tb = new TenatProfileDTO();
		
		// Zelimo da vratimo stanara sa najnovijim podacima
		if(president != null){
			if(tenatHasVoted.getId() == president.getId()){
				tb.setTenat(president);
			}else{
				tb.setTenat(tenatHasVoted);
			}
		}else{
			tb.setTenat(tenatHasVoted);
		}
		
		tb.setApartmen(tenatHasVoted.getApartmen());
		tb.setBuilding(tenatHasVoted.getApartmen().getApartmenBuilding());
		
		return new ResponseEntity<TenatProfileDTO>(tb, HttpStatus.OK);
	}
	
	// Preuzmi sve stanare iz zgrade
	private ArrayList<AppUser> getAllTenat(Building building){
		ArrayList<AppUser> tenats = new ArrayList<>();
		
		for (Apartmen a : building.getApartments()) {
//			System.out.println(a.getName());
			for (AppUser au : a.getApartmen_tenats()) {
				tenats.add(au);
			}
		}
		return tenats;
	}
	
	private AppUser getMostVotedTenat(ArrayList<AppUser> tenats){
		int max = 0;
		AppUser president = new AppUser();
		for (AppUser tenat : tenats) {
			if(tenat.getVotes() > max){
				max = tenat.getVotes();
				president = tenat;
			}
		}
		System.out.println(president.getName());
		return president;
	}
	
	
	
	
}
