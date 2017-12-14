package com.projekat.kts.controller;

import static com.projekat.kts.constants.BuldingConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.projekat.kts.model.Building;
import com.projekat.kts.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuildingControllerTest {

private static final String URL_PREFIX = "/api/buildings";
	
	private MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @PostConstruct
    public void setup() {
    	this.mockMvc = MockMvcBuilders.
    			webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void testGetAllBuildings() throws Exception {
    	mockMvc.perform(get(URL_PREFIX))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$['buildings']", hasSize(DB_COUNT_BUILDING)))
	        .andExpect(jsonPath("$.buildings.[0].name").value(DB_NAME1))
	        .andExpect(jsonPath("$['buildings'][0].location").value(DB_LOCATION1))
	        .andExpect(jsonPath("$['buildings'][0].owner").value(DB_OWNER1))
	        .andExpect(jsonPath("$['buildings'][0].numberOfApartments").value(DB_NUMBER_OF_APARTMENS1))
	        .andExpect(jsonPath("$['buildings'][0].numberOfAparartmentsWithTenats").value(DB_UMBER_OF_APARTMENS_WITH_TENATS1))
	        .andExpect(jsonPath("$['buildings'][0].hasPresident").value(DB_HAS_PRESIDENT1));
    }
    
    @Test
    public void testGetBuilding() throws Exception {
    	mockMvc.perform(get(URL_PREFIX + "/" + DB_ID))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(contentType))
    	.andExpect(jsonPath("$.id").value(DB_ID))
    	.andExpect(jsonPath("$.name").value(DB_NAME))
        .andExpect(jsonPath("$.location").value(DB_LOCATION))
        .andExpect(jsonPath("$.owner").value(DB_OWNER))
        .andExpect(jsonPath("$.numberOfApartments").value(DB_NUMBER_OF_APARTMENS))
        .andExpect(jsonPath("$.numberOfAparartmentsWithTenats").value(DB_UMBER_OF_APARTMENS_WITH_TENATS))
        .andExpect(jsonPath("$.hasPresident").value(DB_HAS_PRESIDENT));
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteBuilding() throws Exception { 	
        this.mockMvc.perform(delete(URL_PREFIX + "/" + DB_ID))
                .andExpect(status().isOk());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveBuilding() throws Exception {
    	Building building = new Building();
    	building.setName(NEW_NAME);
		building.setLocation(NEW_LOCATION);
		building.setNumberOfApartments(NEW_NUMBER_OF_APARTMENS);
		building.setNumberOfApartments(NEW_NUMBER_OF_APARTMENS_WITH_TENATS);
		building.setHasPresident(NEW_HAS_PRESIDENT);
		building.setOwner(NEW_OWNER);
    	
    	String json = TestUtil.json(building);
        this.mockMvc.perform(post(URL_PREFIX)
                .contentType(contentType)
                .content(json))
                .andExpect(status().isCreated());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateBuilding() throws Exception {
    	Building building = new Building();
    	building.setName(NEW_NAME);
		building.setLocation(NEW_LOCATION);
		building.setNumberOfApartments(NEW_NUMBER_OF_APARTMENS);
		building.setNumberOfApartments(NEW_NUMBER_OF_APARTMENS_WITH_TENATS);
		building.setHasPresident(NEW_HAS_PRESIDENT);
		building.setOwner(NEW_OWNER);
    	
    	String json = TestUtil.json(building);
        this.mockMvc.perform(put(URL_PREFIX)
                .contentType(contentType)
                .content(json))
                .andExpect(status().isOk());
    }
}
