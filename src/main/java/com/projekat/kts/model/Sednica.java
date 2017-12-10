package com.projekat.kts.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Predrag Falcic
 *
 */

@Entity
public class Sednica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated; // Datum i vreme kada je sednica kreirana
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateScheduled; // Datum i vreme za kada je sednica zakazana
	
	private boolean active; // Da li je sednica trenutno aktivna ili je prosla vec
	
	@ManyToOne
	@JoinColumn(name = "building_id")
	@JsonIgnoreProperties(value = {"sednice"}, allowSetters=true)
	private Building building; // Kojoj zgradi pripadaju sednice
	
	@ManyToOne
	@JoinColumn(name = "app_user_id")
	@JsonIgnoreProperties(value = {"sednice"}, allowSetters=true)
	private AppUser creator; // Ko je kreirao sednicu
	
	@OneToMany(mappedBy = "sednica")
	@JsonIgnoreProperties(value = {"sednica"}, allowSetters = true)
	@JsonIgnore
	private List<Stavka> stavke; // stavke koje pripadaju sednici
	
	@OneToMany(mappedBy = "sednica")
	@JsonIgnoreProperties(value = {"sednica"}, allowSetters = true)
	@JsonIgnore
	private List<Zapisnik> zapisnici; // zapisnici koje je kreirao
	
	public Sednica() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateScheduled() {
		return dateScheduled;
	}

	public void setDateScheduled(Date dateScheduled) {
		this.dateScheduled = dateScheduled;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public AppUser getCreator() {
		return creator;
	}

	public void setCreator(AppUser creator) {
		this.creator = creator;
	}

	public List<Stavka> getStavke() {
		return stavke;
	}

	public void setStavke(List<Stavka> stavke) {
		this.stavke = stavke;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Zapisnik> getZapisnici() {
		return zapisnici;
	}

	public void setZapisnici(List<Zapisnik> zapisnici) {
		this.zapisnici = zapisnici;
	}

	@Override
	public String toString() {
		return "Sednica [id=" + id + ", dateCreated=" + dateCreated + ", dateScheduled=" + dateScheduled + "]";
	}
}
