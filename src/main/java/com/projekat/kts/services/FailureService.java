package com.projekat.kts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Building;
import com.projekat.kts.model.Failure;
import com.projekat.kts.repository.FailureRepository;

@Service
public class FailureService {

	@Autowired
	private FailureRepository failureRepository;
	
	public List<Failure> getAllFailures(){
		return failureRepository.findAll();
	}
	
	public Failure save(Failure failure){
		return failureRepository.save(failure);
	}
	
	public List<Failure> findByBuilding(Building building){
		return failureRepository.findByBuilding(building);
	}
	
	public List<Failure> findByWorker(AppUser worker){
		return failureRepository.findByWorker(worker);
	}
	
	public Failure findOneById(Long id){
		return failureRepository.findOne(id);
	}
}
