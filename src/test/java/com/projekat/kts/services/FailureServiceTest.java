package com.projekat.kts.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static com.projekat.kts.constants.FailureConstatns.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projekat.kts.model.Failure;
import com.projekat.kts.repository.AppUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FailureServiceTest {

	@Autowired
	private FailureService failureService;
	
	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Test
	public void testFindAll() {
		List<Failure> failures = failureService.getAllFailures();
		assertThat(failures).hasSize(DB_COUNT_FAILURES);
	}
	
	@Test
	public void testOneById() {
		Failure failure = failureService.findOneById(DB_ID);
		assertThat(failure).isNotNull();
		
		assertThat(failure.getId()).isEqualTo(DB_ID);
		assertThat(failure.getName()).isEqualTo(DB_NAME);
		assertThat(failure.getDescription()).isEqualTo(DB_DESCRIPTION);
        assertThat(failure.isSolved()).isEqualTo(DB_HAS_SOLVED);  
        assertThat(failure.isHasWorker()).isEqualTo(DB_HAS_WORKER); 
        assertThat(failure.isHasZakazano()).isEqualTo(DB_HAS_ZAKAZANO); 
	}
	
	@Test
	public void testOneByBuilding() {
		List<Failure> failures = failureService.findByBuilding(buildingService.findOneById(DB_ID_BUILDING));
		assertThat(failures).hasSize(DB_COUNT_BY_BUILDING); 
	}
	
	@Test
	public void testOneByWorker() {
		List<Failure> failures = failureService.findByWorker(appUserRepository.findOne(DB_ID_WORKER));
		assertThat(failures).hasSize(DB_COUNT_BY_WORKER); 
	}

	@Test
    @Transactional
    @Rollback(true) //it can be omitted because it is true by default
	public void testAdd() {
		Failure failure = new Failure();
		failure.setName(NEW_NAME);
		failure.setDescription(NEW_DESCRIPTION);
		failure.setDateCreated(NEW_DATE_CREATED);
		failure.setDateSolved(NEW_DATE_SOLVED);
		failure.setDateZakazano(NEW_DATE_ZAKAZANO);
		failure.setHasWorker(NEW_HAS_WORKER);
		failure.setHasZakazano(NEW_HAS_ZAKAZANO);
		failure.setSolved(NEW_HAS_SOLVED);
		
		int dbSizeBeforeAdd = failureService.getAllFailures().size();
		
		Failure dbFailure = failureService.save(failure);
		assertThat(dbFailure).isNotNull();
				
		// Validate that new failure is in the database
        List<Failure> failures = failureService.getAllFailures();
        assertThat(failures).hasSize(dbSizeBeforeAdd + 1);
        dbFailure = failures.get(failures.size() - 1); //get last failure
        
        assertThat(failure.getName()).isEqualTo(NEW_NAME);
		assertThat(failure.getDescription()).isEqualTo(NEW_DESCRIPTION);
        assertThat(failure.getDateCreated()).isEqualTo(NEW_DATE_CREATED);
        assertThat(failure.getDateSolved()).isEqualTo(NEW_DATE_SOLVED);  
        assertThat(failure.getDateZakazano()).isEqualTo(NEW_DATE_ZAKAZANO);  
        assertThat(failure.isSolved()).isEqualTo(NEW_HAS_SOLVED);  
        assertThat(failure.isHasWorker()).isEqualTo(NEW_HAS_WORKER); 
        assertThat(failure.isHasZakazano()).isEqualTo(NEW_HAS_ZAKAZANO);          
	}
	
	/**
	 * Negativni testovi
	 */
	
	@Test(expected = DataIntegrityViolationException.class)
    @Transactional
    @Rollback(true)
	public void testAddNonUniqueName() {
		Failure failure = new Failure();
		
		failure.setName(DB_NAME); // Postojece ime za apartman
		failure.setDescription(NEW_DESCRIPTION);
		failure.setDateCreated(NEW_DATE_CREATED);
		failure.setDateSolved(NEW_DATE_SOLVED);
		failure.setDateZakazano(NEW_DATE_ZAKAZANO);
		failure.setHasWorker(NEW_HAS_WORKER);
		failure.setHasZakazano(NEW_HAS_ZAKAZANO);
		failure.setSolved(NEW_HAS_SOLVED);
		
		failureService.save(failure);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	@Rollback(true)
	public void testAddNullName() {
		Failure failure = new Failure();
		// Ne upisujemo ime, probamo da bude null jer to ne sme da se dozvoli
		failure.setDescription(NEW_DESCRIPTION);
		failure.setDateCreated(NEW_DATE_CREATED);
		failure.setDateSolved(NEW_DATE_SOLVED);
		failure.setDateZakazano(NEW_DATE_ZAKAZANO);
		failure.setHasWorker(NEW_HAS_WORKER);
		failure.setHasZakazano(NEW_HAS_ZAKAZANO);
		failure.setSolved(NEW_HAS_SOLVED);
		
		failureService.save(failure);
	}
}
