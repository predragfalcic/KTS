package com.projekat.kts.services;

import static com.projekat.kts.constants.SednicaConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projekat.kts.model.Sednica;
import com.projekat.kts.model.Stavka;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SednicaServiceTest {

	@Autowired
	private SednicaService sednicaService;
	
	@Autowired
	private BuildingService buildingService;
	
	@Test
	public void testFindAll() {
		List<Sednica> sednice = sednicaService.findAll();
		assertThat(sednice).hasSize(DB_COUNT_SEDNICA);
	}
	
	@Test
	public void testOneById() {
		Sednica sednica = sednicaService.findById(DB_ID);
		assertThat(sednica).isNotNull();
		
		assertThat(sednica.getId()).isEqualTo(DB_ID);
		assertThat(sednica.isActive()).isEqualTo(DB_ACTIVE);
	}
	
	@Test
	public void testByBuilding() {
		List<Sednica> s = sednicaService.findByBuilding(buildingService.findOneById(DB_ID_BUILDING));
		assertThat(s).hasSize(DB_COUNT_SEDNICA_BY_BUILDING); 
	}
	
	@Test
    @Transactional
    @Rollback(true) //it can be omitted because it is true by default
	public void testAdd() {
		Sednica s = new Sednica();
		s.setDateCreated(NEW_DATE_CREATED);
		s.setDateScheduled(NEW_DATE_SCHEDULED);
		s.setActive(NEW_ACTIVE);
		
		int dbSizeBeforeAdd = sednicaService.findAll().size();
		
		Sednica dbStavka = sednicaService.save(s);
		assertThat(dbStavka).isNotNull();
				
		// Validate that new failure is in the database
        List<Sednica> stavke = sednicaService.findAll();
        assertThat(stavke).hasSize(dbSizeBeforeAdd + 1);
        dbStavka = stavke.get(stavke.size() - 1); //get last failure
        
		assertThat(s.getDateCreated()).isEqualTo(NEW_DATE_CREATED);
        assertThat(s.getDateScheduled()).isEqualTo(NEW_DATE_SCHEDULED); 
        assertThat(s.isActive()).isEqualTo(NEW_ACTIVE);
	}
}
