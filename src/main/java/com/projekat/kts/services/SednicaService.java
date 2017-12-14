package com.projekat.kts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.kts.model.Building;
import com.projekat.kts.model.Sednica;
import com.projekat.kts.repository.SednicaRepository;

@Service
public class SednicaService {

	@Autowired
	private SednicaRepository sednicaRepository;
	
	public List<Sednica> findAll(){
		return sednicaRepository.findAll();
	}
	
	public Sednica save(Sednica sednica){
		return sednicaRepository.save(sednica);
	}
	
	public Sednica findById(Long id){
		return sednicaRepository.findOne(id);
	}
	
	public List<Sednica> findByBuilding(Building building){
		return sednicaRepository.findByBuilding(building);
	}
}
