package com.projekat.kts.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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

@Entity
public class Failure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String description; // Opis kvara
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated; // Datum i vreme kada je kvar kreiran
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSolved; // Datum i vreme kada je kvar popravljen
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateZakazano; // Datum i vreme za kad je popravka kvara zakazana
	
	private boolean solved; // Da li je kvar popravljen
	private boolean hasWorker; // Da li kvar ima radnika koji radi na njemu
	private boolean hasZakazano; // Da li je popravka kvars zakazans za odredjen termin
	
	@ManyToOne
	@JoinColumn(name = "app_user_id")
	@JsonIgnoreProperties(value = {"failures"}, allowSetters=true)
	private AppUser worker; // Radnik koji je zaduzen za kvar
	
	@ManyToOne
	@JoinColumn(name = "institution_id")
	@JsonIgnoreProperties(value = {"failures"}, allowSetters=true)
	private Institution institution; // Institucija kojoj je kvar prijavljen
	
	@ManyToOne
	@JoinColumn(name = "building_id")
	@JsonIgnoreProperties(value = {"failures"}, allowSetters=true)
	private Building building; // Zgrada u kojoj je kvar nastao
	
	@OneToMany(mappedBy = "failure")
	@JsonIgnoreProperties(value = {"failure"}, allowSetters = true)
	@JsonIgnore
	private List<Comment> comments; // Komentare koje je napisao
	
	public Failure(){}

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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateSolved() {
		return dateSolved;
	}

	public void setDateSolved(Date dateSolved) {
		this.dateSolved = dateSolved;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	public AppUser getWorker() {
		return worker;
	}

	public void setWorker(AppUser worker) {
		this.worker = worker;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public boolean isHasWorker() {
		return hasWorker;
	}

	public void setHasWorker(boolean hasWorker) {
		this.hasWorker = hasWorker;
	}

	public boolean isHasZakazano() {
		return hasZakazano;
	}

	public void setHasZakazano(boolean hasZakazano) {
		this.hasZakazano = hasZakazano;
	}

	public Date getDateZakazano() {
		return dateZakazano;
	}

	public void setDateZakazano(Date dateZakazano) {
		this.dateZakazano = dateZakazano;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
