package com.projekat.kts.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Institution {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String location;
	private String director;
	private String email;
	private String contactPhone;
	private String webSiteUrl;
	
	@ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "institution_building",
    	joinColumns = @JoinColumn(name = "institution_id", referencedColumnName = "id"),
    	inverseJoinColumns = @JoinColumn(name = "building_id", referencedColumnName = "id"))
	@JsonIgnoreProperties(value = {"institutions"}, allowSetters=true)
	private Set<Building> buildings; // Buildings which this institution is maintaining
	
	public Institution() {}

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

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getWebSiteUrl() {
		return webSiteUrl;
	}

	public void setWebSiteUrl(String webSiteUrl) {
		this.webSiteUrl = webSiteUrl;
	}

	public Set<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(Set<Building> buildings) {
		this.buildings = buildings;
	}

	@Override
	public String toString() {
		return "Institution [id=" + id + ", name=" + name + ", location=" + location + ", director=" + director
				+ ", email=" + email + ", contactPhone=" + contactPhone + ", webSiteUrl=" + webSiteUrl + ", buildings="
				+ buildings + "]";
	}
	
	
}
