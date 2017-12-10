package com.projekat.kts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.kts.model.Building;
import com.projekat.kts.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	public List<Notification> findByBuilding(Building building);
}
