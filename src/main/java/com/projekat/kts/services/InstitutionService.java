package com.projekat.kts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.kts.model.Institution;
import com.projekat.kts.repository.InstitutionRepository;

@Service
public class InstitutionService {

	@Autowired
	private InstitutionRepository institutionRepository;
	
	public Institution findOneByName(String name) {
		return institutionRepository.findOneByName(name);
	}
	
	public Institution save(Institution institution) {
		return institutionRepository.save(institution);
	}
	
	public List<Institution> findAll(){
		return institutionRepository.findAll();
	}
	
	public void delete(Institution institution) {
		institutionRepository.delete(institution);
	}
	
	public Institution findOneById(Long id) {
		return institutionRepository.findOne(id);
	}
}
