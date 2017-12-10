package com.projekat.kts.dto;

import java.util.Date;

import com.projekat.kts.model.AppUser;

public class SednicaDTO {

	private Date dateScheduled;
	private AppUser creator;
	
	public SednicaDTO(){}

	public Date getDateScheduled() {
		return dateScheduled;
	}

	public void setDateScheduled(Date dateScheduled) {
		this.dateScheduled = dateScheduled;
	}

	public AppUser getCreator() {
		return creator;
	}

	public void setCreator(AppUser creator) {
		this.creator = creator;
	}
}
