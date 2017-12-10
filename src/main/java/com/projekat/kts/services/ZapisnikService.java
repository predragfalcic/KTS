package com.projekat.kts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.kts.model.Sednica;
import com.projekat.kts.model.Zapisnik;
import com.projekat.kts.repository.ZapisnikRepository;

@Service
public class ZapisnikService {

	@Autowired
	private ZapisnikRepository zapisnikRepository;
	
	public List<Zapisnik> findBySednica(Sednica sednica){
		return zapisnikRepository.findBySednica(sednica);
	}
	
	public Zapisnik save(Zapisnik zapisnik){
		return zapisnikRepository.save(zapisnik);
	}
	
	public void delete(Zapisnik zapisnik){
		zapisnikRepository.delete(zapisnik);
	}
}
