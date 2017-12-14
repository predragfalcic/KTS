package com.projekat.kts.services;

import static com.projekat.kts.constants.NotificationConstatns.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projekat.kts.model.Notification;
import com.projekat.kts.model.Sednica;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationServiceTest {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private BuildingService buildingService;
	
	@Test
	public void testByBuilding() {
		List<Notification> s = notificationService.findByBuilding(buildingService.findOneById(DB_ID_BUILDING));
		assertThat(s).hasSize(DB_COUNT_BY_BUILDING); 
	}
	
	@Test
    @Transactional
    @Rollback(true) //it can be omitted because it is true by default
	public void testAdd() {
		Notification s = new Notification();
		s.setDateCreated(NEW_DATE_CREATED);
		s.setDescription(NEW_DESCRIPTION);
		
		int dbSizeBeforeAdd = notificationService.findAll().size();
		
		Notification dbStavka = notificationService.save(s);
		assertThat(dbStavka).isNotNull();
				
		// Validate that new failure is in the database
        List<Notification> stavke = notificationService.findAll();
        assertThat(stavke).hasSize(dbSizeBeforeAdd + 1);
        dbStavka = stavke.get(stavke.size() - 1); //get last failure
        
		assertThat(s.getDateCreated()).isEqualTo(NEW_DATE_CREATED);
        assertThat(s.getDescription()).isEqualTo(NEW_DESCRIPTION);
	}
}
