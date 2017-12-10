package com.projekat.kts.dto;

import com.projekat.kts.model.Zapisnik;

public class ZapisnikDTO {

	private Zapisnik zapisnik;
	private Long sednicaId;
	private Long tenatId;
	
	public ZapisnikDTO(){}

	public Zapisnik getZapisnik() {
		return zapisnik;
	}

	public void setZapisnik(Zapisnik zapisnik) {
		this.zapisnik = zapisnik;
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
