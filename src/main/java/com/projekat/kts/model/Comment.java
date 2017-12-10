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
public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String text;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated; // Datum i vreme kada je komentar kreiran
	
	@ManyToOne
	@JoinColumn(name = "app_user_id")
	@JsonIgnoreProperties(value = {"comments"}, allowSetters=true)
	private AppUser author; // Ko je napisao komentar
	
	@ManyToOne
	@JoinColumn(name = "failure_id")
	@JsonIgnoreProperties(value = {"comments"}, allowSetters=true)
	private Failure failure; // Ko je napisao komentar
	
	public Comment(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public AppUser getAuthor() {
		return author;
	}

	public void setAuthor(AppUser author) {
		this.author = author;
	}

	public Failure getFailure() {
		return failure;
	}

	public void setFailure(Failure failure) {
		this.failure = failure;
	}
}
