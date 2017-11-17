package com.projekat.kts.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
public class Building {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String location;
	private int numberOfApartments;
	
	@OneToMany(mappedBy = "building", fetch=FetchType.EAGER)
	@JsonIgnoreProperties(value = {"building"}, allowSetters = true)
	private Set<AppUser> tenats; // Stanari u zgradi
	
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

	public Set<AppUser> getTenats() {
		return tenats;
	}

	public void setTenats(Set<AppUser> tenats) {
		this.tenats = tenats;
	}

	@Override
	public String toString() {
		return "Building [name=" + name + ", tenats=" + tenats + "]";
	}
	
	
}
