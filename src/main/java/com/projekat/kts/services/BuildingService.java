package com.projekat.kts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.kts.model.Building;
import com.projekat.kts.repository.BuildingRepository;

@Service
public class BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;
	
	public Building findOneByName(String name) {
		return buildingRepository.findOneByName(name);
	}
	
	public List<Building> findAll(){
		return buildingRepository.findAll();
	}
	
	public Building findOneById(Long id) {
		return buildingRepository.findOne(id);
	}
	
	public void delete(Building building) {
		buildingRepository.delete(building);
	}
	
	public Building save(Building building) {
		return buildingRepository.save(building);
	}
}
