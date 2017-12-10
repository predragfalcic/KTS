package com.projekat.kts.dto;

import java.util.List;

import com.projekat.kts.model.Apartmen;
import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Building;
import com.projekat.kts.model.Notification;

/**
 * 
 * @author Predrag Falcic
 *
 * Klasa koja sadrzi korisnika, 
 * apartman u kom zivi i zgradu 
 * u kojoj se nalazi apratman
 */
public class TenatProfileDTO {
	private AppUser tenat;
	private Apartmen apartmen;
	private Building building;
	private List<Notification> notifications;
	
	public TenatProfileDTO(){}

	public AppUser getTenat() {
		return tenat;
	}

	public void setTenat(AppUser tenat) {
		this.tenat = tenat;
	}

	public Apartmen getApartmen() {
		return apartmen;
	}

	public void setApartmen(Apartmen apartmen) {
		this.apartmen = apartmen;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
}
