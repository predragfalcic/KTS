package com.projekat.kts.services;

import static com.projekat.kts.constants.ApartmenConstatns.DB_COUNT_APARTMEN;
import static com.projekat.kts.constants.ApartmenConstatns.DB_HAS_APARTMENT_BUILDING;
import static com.projekat.kts.constants.ApartmenConstatns.DB_HAS_OWNER;
import static com.projekat.kts.constants.ApartmenConstatns.DB_ID;
import static com.projekat.kts.constants.ApartmenConstatns.DB_LOCATION;
import static com.projekat.kts.constants.ApartmenConstatns.DB_NAME;
import static com.projekat.kts.constants.ApartmenConstatns.DB_NUMBER_OF_TENATS;
import static com.projekat.kts.constants.ApartmenConstatns.DB_OWNER;
import static com.projekat.kts.constants.ApartmenConstatns.NEW_HAS_APARTMENT_BUILDING;
import static com.projekat.kts.constants.ApartmenConstatns.NEW_HAS_OWNER;
import static com.projekat.kts.constants.ApartmenConstatns.NEW_LOCATION;
import static com.projekat.kts.constants.ApartmenConstatns.NEW_NAME;
import static com.projekat.kts.constants.ApartmenConstatns.NEW_NUMBER_OF_TENATS;
import static com.projekat.kts.constants.ApartmenConstatns.NEW_OWNER;
import static com.projekat.kts.constants.ApartmenConstatns.DB_COUNT_APARTMEN_HAS_BUILDING;
import static com.projekat.kts.constants.ApartmenConstatns.DB_ID_REFERENCED;

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

import com.projekat.kts.model.Apartmen;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApartmenServiceTest {

	@Autowired
	private ApartmenService apartmenService;
	
	@Test
	public void testFindAll() {
		List<Apartmen> apartmens = apartmenService.findAll();
		assertThat(apartmens).hasSize(DB_COUNT_APARTMEN);
	}
	
	@Test
	public void testOneByName() {
		Apartmen apartmen = apartmenService.findOneByName(DB_NAME);
		assertThat(apartmen).isNotNull();
		
		assertThat(apartmen.getId()).isEqualTo(DB_ID);
		assertThat(apartmen.getName()).isEqualTo(DB_NAME);
        assertThat(apartmen.getNumberOfTenats()).isEqualTo(DB_NUMBER_OF_TENATS);
        assertThat(apartmen.getLocation()).isEqualTo(DB_LOCATION);  
        assertThat(apartmen.getOwner()).isEqualTo(DB_OWNER);  
        assertThat(apartmen.isHasApartmentBuilding()).isEqualTo(DB_HAS_APARTMENT_BUILDING);  
        assertThat(apartmen.isHasOwner()).isEqualTo(DB_HAS_OWNER);  
	}
	
	@Test
	public void testOneById() {
		Apartmen apartmen = apartmenService.findOneById(DB_ID);
		assertThat(apartmen).isNotNull();
		
		assertThat(apartmen.getId()).isEqualTo(DB_ID);
		assertThat(apartmen.getName()).isEqualTo(DB_NAME);
        assertThat(apartmen.getNumberOfTenats()).isEqualTo(DB_NUMBER_OF_TENATS);
        assertThat(apartmen.getLocation()).isEqualTo(DB_LOCATION);  
        assertThat(apartmen.getOwner()).isEqualTo(DB_OWNER);  
        assertThat(apartmen.isHasApartmentBuilding()).isEqualTo(DB_HAS_APARTMENT_BUILDING);  
        assertThat(apartmen.isHasOwner()).isEqualTo(DB_HAS_OWNER);  
	}
	
	@Test
	public void testOneByHasBuilding() {
		List<Apartmen> apartmens = apartmenService.findByHasBuilding(DB_HAS_APARTMENT_BUILDING);
		assertThat(apartmens).hasSize(DB_COUNT_APARTMEN_HAS_BUILDING); 
	}

	@Test
    @Transactional
    @Rollback(true) //it can be omitted because it is true by default
	public void testAdd() {
		Apartmen apartmen = new Apartmen();
		apartmen.setName(NEW_NAME);
		apartmen.setLocation(NEW_LOCATION);
		apartmen.setHasApartmentBuilding(NEW_HAS_APARTMENT_BUILDING);
		apartmen.setHasOwner(NEW_HAS_OWNER);
		apartmen.setNumberOfTenats(NEW_NUMBER_OF_TENATS);
		apartmen.setOwner(NEW_OWNER);
		
		int dbSizeBeforeAdd = apartmenService.findAll().size();
		
		Apartmen dbApartmen = apartmenService.save(apartmen);
		assertThat(dbApartmen).isNotNull();
				
		// Validate that new apartmen is in the database
        List<Apartmen> apartmens = apartmenService.findAll();
        assertThat(apartmens).hasSize(dbSizeBeforeAdd + 1);
        dbApartmen = apartmens.get(apartmens.size() - 1); //get last apartmen
        
        assertThat(apartmen.getName()).isEqualTo(NEW_NAME);
        assertThat(apartmen.getNumberOfTenats()).isEqualTo(NEW_NUMBER_OF_TENATS);
        assertThat(apartmen.getLocation()).isEqualTo(NEW_LOCATION);  
        assertThat(apartmen.getOwner()).isEqualTo(NEW_OWNER);  
        assertThat(apartmen.isHasApartmentBuilding()).isEqualTo(NEW_HAS_APARTMENT_BUILDING);  
        assertThat(apartmen.isHasOwner()).isEqualTo(NEW_HAS_OWNER);          
	}
	
	@Test
    @Transactional
    @Rollback(true)
	public void testUpdate() {
		Apartmen apartmen = apartmenService.findOneById(DB_ID);
		
		apartmen.setName(NEW_NAME);
		apartmen.setLocation(NEW_LOCATION);
		apartmen.setHasApartmentBuilding(NEW_HAS_APARTMENT_BUILDING);
		apartmen.setHasOwner(NEW_HAS_OWNER);
		apartmen.setNumberOfTenats(NEW_NUMBER_OF_TENATS);
		apartmen.setOwner(NEW_OWNER);
		
		apartmen = apartmenService.save(apartmen);
		assertThat(apartmen).isNotNull();
		
		//verify that database contains updated data
		apartmen = apartmenService.findOneById(DB_ID);

		assertThat(apartmen.getName()).isEqualTo(NEW_NAME);
        assertThat(apartmen.getNumberOfTenats()).isEqualTo(NEW_NUMBER_OF_TENATS);
        assertThat(apartmen.getLocation()).isEqualTo(NEW_LOCATION);  
        assertThat(apartmen.getOwner()).isEqualTo(NEW_OWNER);  
        assertThat(apartmen.isHasApartmentBuilding()).isEqualTo(NEW_HAS_APARTMENT_BUILDING);  
        assertThat(apartmen.isHasOwner()).isEqualTo(NEW_HAS_OWNER);   
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testRemove() {
		int dbSizeBeforeRemove = apartmenService.findAll().size();
		Apartmen apartmen = apartmenService.findOneById(DB_ID);
		apartmenService.delete(apartmen);
		
		List<Apartmen> apartmens = apartmenService.findAll();
		assertThat(apartmens).hasSize(dbSizeBeforeRemove - 1);
		
		Apartmen dbApartmen = apartmenService.findOneById(DB_ID);
		assertThat(dbApartmen).isNull();
	}
	
	/**
	 * Negativni testovi
	 */
	
	@Test(expected = DataIntegrityViolationException.class)
    @Transactional
    @Rollback(true)
	public void testAddNonUniqueName() {
		Apartmen apartmen = new Apartmen();
		
		apartmen.setName(DB_NAME); // Postojece ime za apartman
		apartmen.setLocation(NEW_LOCATION);
		apartmen.setHasApartmentBuilding(NEW_HAS_APARTMENT_BUILDING);
		apartmen.setHasOwner(NEW_HAS_OWNER);
		apartmen.setNumberOfTenats(NEW_NUMBER_OF_TENATS);
		apartmen.setOwner(NEW_OWNER);
		
		apartmenService.save(apartmen);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	@Rollback(true)
	public void testAddNullName() {
		Apartmen apartmen = new Apartmen();
		// Ne upisujemo ime, probamo da bude null jer to ne sme da se dozvoli
		apartmen.setLocation(NEW_LOCATION);
		apartmen.setHasApartmentBuilding(NEW_HAS_APARTMENT_BUILDING);
		apartmen.setHasOwner(NEW_HAS_OWNER);
		apartmen.setNumberOfTenats(NEW_NUMBER_OF_TENATS);
		apartmen.setOwner(NEW_OWNER);
		
		apartmenService.save(apartmen);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	@Rollback(true)
	public void testRemoveNegative() {
		int dbSizeBeforeRemove = apartmenService.findAll().size();
		Apartmen apartmen = apartmenService.findOneById(DB_ID_REFERENCED);
		apartmenService.delete(apartmen); // pukusavamo da obrisemo apartman koji ima referencu ka nekim entitetima
		
		List<Apartmen> apartmens = apartmenService.findAll();
		assertThat(apartmens).hasSize(dbSizeBeforeRemove - 1);
		
		Apartmen dbApartmen = apartmenService.findOneById(DB_ID_REFERENCED);
		assertThat(dbApartmen).isNull();
	}
}
