package com.projekat.kts.services;

import static com.projekat.kts.constants.StavkaConstatns.DB_COUNT_BY_SEDNICA;
import static com.projekat.kts.constants.StavkaConstatns.DB_ID_SEDNICA;
import static com.projekat.kts.constants.StavkaConstatns.NEW_DATE_CREATED;
import static com.projekat.kts.constants.StavkaConstatns.NEW_DESCRIPTION;
import static com.projekat.kts.constants.StavkaConstatns.NEW_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projekat.kts.model.Stavka;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StavkaServiceTest {

	@Autowired
	private StavkaService stavkaService;
	
	@Autowired
	private SednicaService sednicaService;

	@Test
	public void testBySednica() {
		List<Stavka> stavke = stavkaService.findBySednica(sednicaService.findById(DB_ID_SEDNICA));
		assertThat(stavke).hasSize(DB_COUNT_BY_SEDNICA); 
	}

	@Test
    @Transactional
    @Rollback(true) //it can be omitted because it is true by default
	public void testAdd() {
		Stavka stavka = new Stavka();
		stavka.setName(NEW_NAME);
		stavka.setDescription(NEW_DESCRIPTION);
		stavka.setDateCreated(NEW_DATE_CREATED);
		
		int dbSizeBeforeAdd = stavkaService.findAll().size();
		
		Stavka dbStavka = stavkaService.save(stavka);
		assertThat(dbStavka).isNotNull();
				
		// Validate that new failure is in the database
        List<Stavka> stavke = stavkaService.findAll();
        assertThat(stavke).hasSize(dbSizeBeforeAdd + 1);
        dbStavka = stavke.get(stavke.size() - 1); //get last failure
        
        assertThat(stavka.getName()).isEqualTo(NEW_NAME);
		assertThat(stavka.getDescription()).isEqualTo(NEW_DESCRIPTION);
        assertThat(stavka.getDateCreated()).isEqualTo(NEW_DATE_CREATED);         
	}
}
