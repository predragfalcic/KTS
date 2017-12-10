package com.projekat.kts.dto;

import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Notification;

public class NotificationDTO {

	private AppUser tenat; 
	private Notification notification;
	
	public NotificationDTO() {}

	public AppUser getTenat() {
		return tenat;
	}

	public void setTenat(AppUser tenat) {
		this.tenat = tenat;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}
}
