package com.projekat.kts.dto;

import java.util.List;

import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Sednica;
import com.projekat.kts.model.Stavka;
import com.projekat.kts.model.Zapisnik;

public class SednicaDetailsDTO {

	private List<Stavka> stavke;
	private List<Zapisnik> zapisnici;
	private AppUser tenat; 
	private Sednica sednica;
	
	public SednicaDetailsDTO() {}

	public List<Stavka> getStavke() {
		return stavke;
	}

	public void setStavke(List<Stavka> stavke) {
		this.stavke = stavke;
	}

	public List<Zapisnik> getZapisnici() {
		return zapisnici;
	}

	public void setZapisnici(List<Zapisnik> zapisnici) {
		this.zapisnici = zapisnici;
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
}
