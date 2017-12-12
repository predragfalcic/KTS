package com.projekat.kts.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Apartmen implements Serializable {

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
	private String owner;
	private int numberOfTenats;
	private boolean hasApartmentBuilding;
	private boolean hasOwner;
	
	@ManyToOne
	@JoinColumn(name = "building_id")
	@JsonIgnoreProperties(value = {"apartments"}, allowSetters=true)
	private Building apartmenBuilding;
	
	@OneToMany(mappedBy = "apartmen")
	@JsonIgnoreProperties(value = {"apartmen"}, allowSetters = true)
	private Set<AppUser> apartmen_tenats;
	
	public Apartmen(){}

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

	public int getNumberOfTenats() {
		return numberOfTenats;
	}

	public void setNumberOfTenats(int numberOfTenats) {
		this.numberOfTenats = numberOfTenats;
	}

	public Set<AppUser> getApartmen_tenats() {
		return apartmen_tenats;
	}

	public void setApartmen_tenats(Set<AppUser> apartmen_tenats) {
		this.apartmen_tenats = apartmen_tenats;
	}
	
	public boolean isHasApartmentBuilding() {
		return hasApartmentBuilding;
	}

	public void setHasApartmentBuilding(boolean hasApartmentBuilding) {
		this.hasApartmentBuilding = hasApartmentBuilding;
	}

	public Building getApartmenBuilding() {
		return apartmenBuilding;
	}

	public void setApartmenBuilding(Building apartmenBuilding) {
		this.apartmenBuilding = apartmenBuilding;
	}

	public boolean isHasOwner() {
		return hasOwner;
	}

	public void setHasOwner(boolean hasOwner) {
		this.hasOwner = hasOwner;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}

