package com.projekat.kts.controller;

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

import com.projekat.kts.model.Apartmen;
import com.projekat.kts.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApartmenControllerTest {

private static final String URL_PREFIX = "/api/apartmens";
	
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
    public void testGetAllApartmens() throws Exception {
    	mockMvc.perform(get(URL_PREFIX))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$['apartmens']", hasSize(DB_COUNT_APARTMEN)))
	        .andExpect(jsonPath("$.apartmens.[0].name").value(DB_NAME))
	        .andExpect(jsonPath("$['apartmens'][0].location").value(DB_LOCATION))
	        .andExpect(jsonPath("$['apartmens'][0].owner").value(DB_OWNER))
	        .andExpect(jsonPath("$['apartmens'][0].numberOfTenats").value(DB_NUMBER_OF_TENATS))
	        .andExpect(jsonPath("$['apartmens'][0].hasApartmentBuilding").value(DB_HAS_APARTMENT_BUILDING))
	        .andExpect(jsonPath("$['apartmens'][0].hasOwner").value(DB_HAS_OWNER));
    }
    
    @Test
    public void testGetApartmen() throws Exception {
    	mockMvc.perform(get(URL_PREFIX + "/" + DB_ID))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(contentType))
    	.andExpect(jsonPath("$.id").value(DB_ID))
    	.andExpect(jsonPath("$.name").value(DB_NAME))
        .andExpect(jsonPath("$.location").value(DB_LOCATION))
        .andExpect(jsonPath("$.owner").value(DB_OWNER))
        .andExpect(jsonPath("$.numberOfTenats").value(DB_NUMBER_OF_TENATS))
        .andExpect(jsonPath("$.hasApartmentBuilding").value(DB_HAS_APARTMENT_BUILDING))
        .andExpect(jsonPath("$.hasOwner").value(DB_HAS_OWNER));
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteApartmen() throws Exception { 	
        this.mockMvc.perform(delete(URL_PREFIX + "/" + DB_ID))
                .andExpect(status().isOk());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveApartmen() throws Exception {
    	Apartmen apartmen = new Apartmen();
    	apartmen.setName(NEW_NAME);
		apartmen.setLocation(NEW_LOCATION);
		apartmen.setHasApartmentBuilding(NEW_HAS_APARTMENT_BUILDING);
		apartmen.setHasOwner(NEW_HAS_OWNER);
		apartmen.setNumberOfTenats(NEW_NUMBER_OF_TENATS);
		apartmen.setOwner(NEW_OWNER);
    	
    	String json = TestUtil.json(apartmen);
        this.mockMvc.perform(post(URL_PREFIX)
                .contentType(contentType)
                .content(json))
                .andExpect(status().isCreated());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateApartmen() throws Exception {
    	Apartmen apartmen = new Apartmen();
    	apartmen.setName(NEW_NAME);
		apartmen.setLocation(NEW_LOCATION);
		apartmen.setHasApartmentBuilding(NEW_HAS_APARTMENT_BUILDING);
		apartmen.setHasOwner(NEW_HAS_OWNER);
		apartmen.setNumberOfTenats(NEW_NUMBER_OF_TENATS);
		apartmen.setOwner(NEW_OWNER);
    	
    	String json = TestUtil.json(apartmen);
        this.mockMvc.perform(put(URL_PREFIX)
                .contentType(contentType)
                .content(json))
                .andExpect(status().isOk());
    }
}
