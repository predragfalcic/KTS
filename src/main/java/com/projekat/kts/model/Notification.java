package com.projekat.kts.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Obavestenja koja pisu stanari zgrade i dostupna su svima
 * @author Predrag Falcic
 *
 */
@Entity
public class Notification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "app_user_id")
	@JsonIgnoreProperties(value = {"notifications"}, allowSetters=true)
	private AppUser tenat; // Ko je napisao obavestenje
	
	@ManyToOne
	@JoinColumn(name = "building_id")
	@JsonIgnoreProperties(value = {"notifications"}, allowSetters=true)
	private Building building; // Kojoj zgradi pripadaju obavestenja
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated; // Datum i vreme kada je obavestenje kreirano
	
	public Notification(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AppUser getTenat() {
		return tenat;
	}

	public void setTenat(AppUser tenat) {
		this.tenat = tenat;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}
}
