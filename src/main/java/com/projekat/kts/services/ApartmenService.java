package com.projekat.kts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.kts.model.Apartmen;
import com.projekat.kts.repository.ApartmentRepository;

@Service
public class ApartmenService {

	@Autowired
	private ApartmentRepository apartmenRepository;
	
	public Apartmen findOneByName(String name){
		return apartmenRepository.findOneByName(name);
	}
	
	public List<Apartmen> findAll(){
		return apartmenRepository.findAll();
	}
	
	public Apartmen findOneById(Long id) {
		return apartmenRepository.findOne(id);
	}
	
	public void delete(Apartmen apartmen) {
		apartmenRepository.delete(apartmen);
	}
	
	public Apartmen save(Apartmen apartmen) {
		return apartmenRepository.save(apartmen);
	} 
	
	public List<Apartmen> findByHasBuilding(boolean hasBuilding){
		return apartmenRepository.findByHasApartmentBuilding(hasBuilding);
	}
}
