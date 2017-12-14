package com.projekat.kts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.kts.model.Building;
import com.projekat.kts.model.Notification;
import com.projekat.kts.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	public Notification save(Notification notification){
		return notificationRepository.save(notification);
	}
	
	public List<Notification> findByBuilding(Building building){
		return notificationRepository.findByBuilding(building);
	}
	
	public List<Notification> findAll(){
		return notificationRepository.findAll();
	}
}
