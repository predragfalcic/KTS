package com.projekat.kts.services;

import static com.projekat.kts.constants.BuldingConstants.*;
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
import com.projekat.kts.model.Building;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuildingServiceTest {

	@Autowired
	private BuildingService buildingService;
	
	@Test
	public void testFindAll() {
		List<Building> buildings = buildingService.findAll();
		assertThat(buildings).hasSize(DB_COUNT_BUILDING);
	}
	
	@Test
	public void testOneByName() {
		Building building = buildingService.findOneByName(DB_NAME);
		assertThat(building).isNotNull();
		
		assertThat(building.getId()).isEqualTo(DB_ID);
		assertThat(building.getName()).isEqualTo(DB_NAME);
        assertThat(building.getLocation()).isEqualTo(DB_LOCATION);  
        assertThat(building.getOwner()).isEqualTo(DB_OWNER); 
        assertThat(building.getNumberOfApartments()).isEqualTo(DB_NUMBER_OF_APARTMENS); 
        assertThat(building.getNumberOfAparartmentsWithTenats()).isEqualTo(DB_UMBER_OF_APARTMENS_WITH_TENATS); 
	}
	
	@Test
	public void testOneById() {
		Building building = buildingService.findOneById(DB_ID);
		assertThat(building).isNotNull();
		
		assertThat(building.getId()).isEqualTo(DB_ID);
		assertThat(building.getName()).isEqualTo(DB_NAME);
        assertThat(building.getLocation()).isEqualTo(DB_LOCATION);  
        assertThat(building.getOwner()).isEqualTo(DB_OWNER); 
        assertThat(building.getNumberOfApartments()).isEqualTo(DB_NUMBER_OF_APARTMENS); 
        assertThat(building.getNumberOfAparartmentsWithTenats()).isEqualTo(DB_UMBER_OF_APARTMENS_WITH_TENATS); 
	}

	@Test
    @Transactional
    @Rollback(true) //it can be omitted because it is true by default
	public void testAdd() {
		Building building = new Building();
		building.setName(NEW_NAME);
		building.setLocation(NEW_LOCATION);
		building.setOwner(NEW_OWNER);
		building.setNumberOfApartments(NEW_NUMBER_OF_APARTMENS);
		building.setNumberOfAparartmentsWithTenats(NEW_NUMBER_OF_APARTMENS_WITH_TENATS);
		
		int dbSizeBeforeAdd = buildingService.findAll().size();
		
		Building dbBuilding = buildingService.save(building);
		assertThat(dbBuilding).isNotNull();
				
		// Validate that new building is in the database
        List<Building> buildings = buildingService.findAll();
        assertThat(buildings).hasSize(dbSizeBeforeAdd + 1);
        dbBuilding = buildings.get(buildings.size() - 1); //get last building
        
		assertThat(building.getName()).isEqualTo(NEW_NAME);
        assertThat(building.getLocation()).isEqualTo(NEW_LOCATION);  
        assertThat(building.getOwner()).isEqualTo(NEW_OWNER); 
        assertThat(building.getNumberOfApartments()).isEqualTo(NEW_NUMBER_OF_APARTMENS); 
        assertThat(building.getNumberOfAparartmentsWithTenats()).isEqualTo(NEW_NUMBER_OF_APARTMENS_WITH_TENATS);          
	}
	
	@Test
    @Transactional
    @Rollback(true)
	public void testUpdate() {
		Building building = buildingService.findOneById(DB_ID);
		
		building.setName(NEW_NAME);
		building.setLocation(NEW_LOCATION);
		building.setOwner(NEW_OWNER);
		building.setNumberOfApartments(NEW_NUMBER_OF_APARTMENS);
		building.setNumberOfAparartmentsWithTenats(NEW_NUMBER_OF_APARTMENS_WITH_TENATS);
		
		building = buildingService.save(building);
		assertThat(building).isNotNull();
		
		//verify that database contains updated data
		building = buildingService.findOneById(DB_ID);

		assertThat(building.getName()).isEqualTo(NEW_NAME);
        assertThat(building.getLocation()).isEqualTo(NEW_LOCATION);  
        assertThat(building.getOwner()).isEqualTo(NEW_OWNER); 
        assertThat(building.getNumberOfApartments()).isEqualTo(NEW_NUMBER_OF_APARTMENS); 
        assertThat(building.getNumberOfAparartmentsWithTenats()).isEqualTo(NEW_NUMBER_OF_APARTMENS_WITH_TENATS);   
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testRemove() {
		int dbSizeBeforeRemove = buildingService.findAll().size();
		Building building = buildingService.findOneById(DB_ID);
		buildingService.delete(building);
		
		List<Building> buildings = buildingService.findAll();
		assertThat(buildings).hasSize(dbSizeBeforeRemove - 1);
		
		Building dbBuilding = buildingService.findOneById(DB_ID);
		assertThat(dbBuilding).isNull();
	}
	
	/**
	 * Negativni testovi
	 */
	
	@Test(expected = DataIntegrityViolationException.class)
    @Transactional
    @Rollback(true)
	public void testAddNonUniqueName() {
		Building building = new Building();
		
		building.setName(DB_NAME);
		building.setLocation(NEW_LOCATION);
		building.setOwner(NEW_OWNER);
		building.setNumberOfApartments(NEW_NUMBER_OF_APARTMENS);
		building.setNumberOfAparartmentsWithTenats(NEW_NUMBER_OF_APARTMENS_WITH_TENATS);
		
		buildingService.save(building);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	@Rollback(true)
	public void testAddNullName() {
		Building building = new Building();
		// Ne upisujemo ime, probamo da bude null jer to ne sme da se dozvoli
		building.setLocation(NEW_LOCATION);
		building.setOwner(NEW_OWNER);
		building.setNumberOfApartments(NEW_NUMBER_OF_APARTMENS);
		building.setNumberOfAparartmentsWithTenats(NEW_NUMBER_OF_APARTMENS_WITH_TENATS);
		
		buildingService.save(building);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	@Rollback(true)
	public void testRemoveNegative() {
		int dbSizeBeforeRemove = buildingService.findAll().size();
		Building building = buildingService.findOneById(DB_ID_REFERENCED);
		buildingService.delete(building); // pukusavamo da obrisemo zgradu koja ima referencu ka nekim entitetima
		
		List<Building> buildings = buildingService.findAll();
		assertThat(buildings).hasSize(dbSizeBeforeRemove - 1);
		
		Building dbBuilding = buildingService.findOneById(DB_ID_REFERENCED);
		assertThat(dbBuilding).isNull();
	}
}
