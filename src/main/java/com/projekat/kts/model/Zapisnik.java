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
public class Zapisnik implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated; // Datum i vreme kada je zapisnik kreiran
	
	@ManyToOne
	@JoinColumn(name = "app_user_id")
	@JsonIgnoreProperties(value = {"zapisnici"}, allowSetters=true)
	private AppUser creator; // Ko je kreirao sednicu
	
	@ManyToOne
	@JoinColumn(name = "sednica_id")
	@JsonIgnoreProperties(value = {"zapisnici"}, allowSetters=true)
	private Sednica sednica; // Kojoj sednici pripada zapisnik
	
	public Zapisnik(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AppUser getCreator() {
		return creator;
	}

	public void setCreator(AppUser creator) {
		this.creator = creator;
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
