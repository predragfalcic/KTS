package com.projekat.kts.services;

import static com.projekat.kts.constants.InstitutionConstatns.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projekat.kts.model.Institution;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InstitutionServiceTest {

	@Autowired
	private InstitutionService institutionService;
	
	@Test
	public void testFindAll() {
		List<Institution> institutions = institutionService.findAll();
		assertThat(institutions).hasSize(DB_COUNT_INSTITUTION);
	}
	
	@Test
	public void testOneByName() {
		Institution institution = institutionService.findOneByName(DB_NAME);
		assertThat(institution).isNotNull();
		
		assertThat(institution.getId()).isEqualTo(DB_ID);
		assertThat(institution.getName()).isEqualTo(DB_NAME);
        assertThat(institution.getContactPhone()).isEqualTo(DB_PHONE_NUMBER);
        assertThat(institution.getLocation()).isEqualTo(DB_LOCATION);  
        assertThat(institution.getDirector()).isEqualTo(DB_DIRECTOR);  
        assertThat(institution.getEmail()).isEqualTo(DB_EMAIL);  
        assertThat(institution.getWebSiteUrl()).isEqualTo(DB_WEBSITE);  
	}
	
	@Test
	public void testOneById() {
		Institution institution = institutionService.findOneById(DB_ID);
		assertThat(institution).isNotNull();
		
		assertThat(institution.getName()).isEqualTo(DB_NAME);
        assertThat(institution.getContactPhone()).isEqualTo(DB_PHONE_NUMBER);
        assertThat(institution.getLocation()).isEqualTo(DB_LOCATION);  
        assertThat(institution.getDirector()).isEqualTo(DB_DIRECTOR);  
        assertThat(institution.getEmail()).isEqualTo(DB_EMAIL);  
        assertThat(institution.getWebSiteUrl()).isEqualTo(DB_WEBSITE);
	}

	@Test
    @Transactional
    @Rollback(true) //it can be omitted because it is true by default
	public void testAdd() {
		Institution institution = new Institution();
		institution.setName(NEW_NAME);
		institution.setLocation(NEW_LOCATION);
		institution.setContactPhone(NEW_PHONE_NUMBER);
		institution.setDirector(NEW_DIRECTOR);
		institution.setWebSiteUrl(NEW_WEBSITE);
		institution.setEmail(NEW_EMAIL);
		
		int dbSizeBeforeAdd = institutionService.findAll().size();
		
		Institution dbInstitution = institutionService.save(institution);
		assertThat(dbInstitution).isNotNull();
				
		// Validate that new institution is in the database
        List<Institution> institutions = institutionService.findAll();
        assertThat(institutions).hasSize(dbSizeBeforeAdd + 1);
        dbInstitution = institutions.get(institutions.size() - 1); //get last institution
        
        assertThat(institution.getName()).isEqualTo(NEW_NAME);
        assertThat(institution.getContactPhone()).isEqualTo(NEW_PHONE_NUMBER);
        assertThat(institution.getLocation()).isEqualTo(NEW_LOCATION);  
        assertThat(institution.getDirector()).isEqualTo(NEW_DIRECTOR);  
        assertThat(institution.getEmail()).isEqualTo(NEW_EMAIL);  
        assertThat(institution.getWebSiteUrl()).isEqualTo(NEW_WEBSITE);         
	}
	
	@Test
    @Transactional
    @Rollback(true)
	public void testUpdate() {
		Institution institution = institutionService.findOneById(DB_ID);
		
		institution.setName(NEW_NAME);
		institution.setLocation(NEW_LOCATION);
		institution.setContactPhone(NEW_PHONE_NUMBER);
		institution.setDirector(NEW_DIRECTOR);
		institution.setWebSiteUrl(NEW_WEBSITE);
		institution.setEmail(NEW_EMAIL);
		
		institution = institutionService.save(institution);
		assertThat(institution).isNotNull();
		
		//verify that database contains updated data
		institution = institutionService.findOneById(DB_ID);

		assertThat(institution.getName()).isEqualTo(NEW_NAME);
        assertThat(institution.getContactPhone()).isEqualTo(NEW_PHONE_NUMBER);
        assertThat(institution.getLocation()).isEqualTo(NEW_LOCATION);  
        assertThat(institution.getDirector()).isEqualTo(NEW_DIRECTOR);  
        assertThat(institution.getEmail()).isEqualTo(NEW_EMAIL);  
        assertThat(institution.getWebSiteUrl()).isEqualTo(NEW_WEBSITE);  
	}
	
	/**
	 * Negativni testovi
	 */
	
	@Test(expected = DataIntegrityViolationException.class)
    @Transactional
    @Rollback(true)
	public void testAddNonUniqueName() {
		Institution institution = new Institution();
		
		institution.setName(DB_NAME);
		institution.setLocation(NEW_LOCATION);
		institution.setContactPhone(NEW_PHONE_NUMBER);
		institution.setDirector(NEW_DIRECTOR);
		institution.setWebSiteUrl(NEW_WEBSITE);
		institution.setEmail(NEW_EMAIL);
		
		institutionService.save(institution);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	@Rollback(true)
	public void testAddNullName() {
		Institution institution = new Institution();
		// Ne upisujemo ime, probamo da bude null jer to ne sme da se dozvoli
		institution.setLocation(NEW_LOCATION);
		institution.setContactPhone(NEW_PHONE_NUMBER);
		institution.setDirector(NEW_DIRECTOR);
		institution.setWebSiteUrl(NEW_WEBSITE);
		institution.setEmail(NEW_EMAIL);
		
		institutionService.save(institution);
	}
}
