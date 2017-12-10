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
 * 
 * @author Predrag Falcic
 *
 */

@Entity
public class Stavka implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	@ManyToOne
	@JoinColumn(name = "app_user_id")
	@JsonIgnoreProperties(value = {"stavke"}, allowSetters=true)
	private AppUser tenat; // Ko je napisao stavku
	
	@ManyToOne
	@JoinColumn(name = "sednica_id")
	@JsonIgnoreProperties(value = {"stavke"}, allowSetters=true)
	private Sednica sednica; // Kojoj sednici pripada stavka
	
	public Stavka(){}

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

	public Sednica getSednica() {
		return sednica;
	}

	public void setSednica(Sednica sednica) {
		this.sednica = sednica;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
