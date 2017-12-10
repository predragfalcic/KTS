package com.projekat.kts.dto;

import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Stavka;

public class StavkaDTO {

	private Stavka stavka;
	private Long sednicaId;
	private Long tenatId;
	
	public StavkaDTO(){}

	public Stavka getStavka() {
		return stavka;
	}

	public void setStavka(Stavka stavka) {
		this.stavka = stavka;
	}

	public Long getSednicaId() {
		return sednicaId;
	}

	public void setSednicaId(Long sednicaId) {
		this.sednicaId = sednicaId;
	}

	public Long getTenatId() {
		return tenatId;
	}

	public void setTenatId(Long tenatId) {
		this.tenatId = tenatId;
	}
}
