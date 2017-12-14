package com.projekat.kts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.kts.model.Apartmen;
import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Building;
import com.projekat.kts.repository.AppUserRepository;
import com.projekat.kts.services.ApartmenService;
import com.projekat.kts.services.BuildingService;

@RestController
@RequestMapping(value = "/api")
public class AppUserController {

	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private ApartmenService apartmentService;
	
	@Autowired
	private BuildingService buildingService;
	
	/**
	 * Web service for getting all the appUsers in the application.
	 * 
	 * @return list of all AppUser
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<AppUser> users() {
		return appUserRepository.findAll();
	}

	/**
	 * Web service for getting a user by his ID
	 * 
	 * @param id
	 *            appUser ID
	 * @return appUser
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<AppUser> userById(@PathVariable Long id) {
		AppUser appUser = appUserRepository.findOne(id);
		if (appUser == null) {
			return new ResponseEntity<AppUser>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<AppUser>(appUser, HttpStatus.OK);
		}
	}

	/**
	 * Method for deleting a user by his ID
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<AppUser> deleteUser(@PathVariable Long id) {
		AppUser appUser = appUserRepository.findOne(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUsername = auth.getName();
		if (appUser == null) {
			return new ResponseEntity<AppUser>(HttpStatus.NO_CONTENT);
		} else if (appUser.getUsername().equalsIgnoreCase(loggedUsername)) {
			throw new RuntimeException("You cannot delete your account");
		} else {
			if(appUser.getApartmen() != null){
				Apartmen apartmen = appUser.getApartmen();
				if(apartmen.getOwner() != null){
					if(apartmen.getOwner().equals(appUser.getName())){
						apartmen.setOwner(null);
						apartmen.setHasOwner(false);
						if(apartmen.getApartmenBuilding() != null){
							Building building = apartmen.getApartmenBuilding();
							building.setHasPresident(false);
							buildingService.save(building);
						}
						apartmentService.save(apartmen);
					}
				}
			}
			appUserRepository.delete(appUser);
			return new ResponseEntity<AppUser>(appUser, HttpStatus.OK);
		}
	}

	/**
	 * Method for adding a appUser
	 * 
	 * @param appUser
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<AppUser> createUser(@RequestBody AppUser appUser) {
		if (appUserRepository.findOneByUsername(appUser.getUsername()) != null) {
			throw new RuntimeException("Username already exist");
		}
		return new ResponseEntity<AppUser>(appUserRepository.save(appUser), HttpStatus.CREATED);
	}

	/**
	 * Method for editing an user details
	 * 
	 * @param appUser
	 * @return modified appUser
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public AppUser updateUser(@RequestBody AppUser appUser) {
		// Check if the user already exists with that username
		if (appUserRepository.findOneByUsername(appUser.getUsername()) != null
				&& appUserRepository.findOneByUsername(appUser.getUsername()).getId() != appUser.getId()) {
			throw new RuntimeException("Username already exist");
		}
		return appUserRepository.save(appUser);
	}
}
