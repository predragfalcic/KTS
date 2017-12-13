package com.projekat.kts.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
public class Building implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String name;
	private String location;
	private String owner; // Vlasnik zgrade
	private int numberOfApartments;
	private int numberOfAparartmentsWithTenats; // Broj stanova koji su naseljeni
	private boolean hasPresident; // Oznacava da li zgrada ima predsednika skupstine stanara
	
	@OneToMany(mappedBy = "apartmenBuilding")
	@JsonIgnoreProperties(value = {"apartmenBuilding"}, allowSetters = true)
	private Set<Apartmen> apartments; // stanovi u zgradi
	
	@ManyToMany(mappedBy = "buildings")
	@JsonIgnoreProperties(value = {"buildings"}, allowSetters = true)
	private Set<Institution> institutions;
	
	@OneToMany(mappedBy = "building")
	@JsonIgnoreProperties(value = {"building"}, allowSetters = true)
	@JsonIgnore
	private List<Failure> failures; // Kvarovi koji su nastali u zgradi
	
	@OneToMany(mappedBy = "building")
	@JsonIgnoreProperties(value = {"building"}, allowSetters = true)
	@JsonIgnore
	private List<Notification> notifications; // Obavestenja koja je postavio
	
	@OneToMany(mappedBy = "building")
	@JsonIgnoreProperties(value = {"building"}, allowSetters = true)
	@JsonIgnore
	private List<Sednica> sednice; // Sednice koje pripadaju zgradi
	
	public Building() {}

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getNumberOfApartments() {
		return numberOfApartments;
	}

	public void setNumberOfApartments(int numberOfApartments) {
		this.numberOfApartments = numberOfApartments;
	}

	public Set<Institution> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(Set<Institution> institutions) {
		this.institutions = institutions;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Set<Apartmen> getApartments() {
		return apartments;
	}

	public void setApartments(Set<Apartmen> apartments) {
		this.apartments = apartments;
	}

	public int getNumberOfAparartmentsWithTenats() {
		return numberOfAparartmentsWithTenats;
	}

	public void setNumberOfAparartmentsWithTenats(int numberOfAparartmentsWithTenats) {
		this.numberOfAparartmentsWithTenats = numberOfAparartmentsWithTenats;
	}
	
	public boolean isHasPresident() {
		return hasPresident;
	}

	public void setHasPresident(boolean hasPresident) {
		this.hasPresident = hasPresident;
	}
	
	public List<Failure> getFailures() {
		return failures;
	}

	public void setFailures(List<Failure> failures) {
		this.failures = failures;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	@Override
	public String toString() {
		return "Building [name=" + name + ", apartments=" + apartments + "]";
	}

	public List<Sednica> getSednice() {
		return sednice;
	}

	public void setSednice(List<Sednica> sednice) {
		this.sednice = sednice;
	}
}
