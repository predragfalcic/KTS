package com.projekat.kts.controller;

import static com.projekat.kts.constants.InstitutionConstatns.*;
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

import com.projekat.kts.model.Institution;
import com.projekat.kts.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InstitutionControllerTest {

private static final String URL_PREFIX = "/api/institutions";
	
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
    public void testGetAllInstitutions() throws Exception {
    	mockMvc.perform(get(URL_PREFIX))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$['institutions']", hasSize(DB_COUNT_INSTITUTION)))
	        .andExpect(jsonPath("$.institutions.[0].name").value(DB_NAME))
	        .andExpect(jsonPath("$['institutions'][0].location").value(DB_LOCATION))
	        .andExpect(jsonPath("$['institutions'][0].director").value(DB_DIRECTOR))
	        .andExpect(jsonPath("$['institutions'][0].email").value(DB_EMAIL))
	        .andExpect(jsonPath("$['institutions'][0].contactPhone").value(DB_PHONE_NUMBER))
	        .andExpect(jsonPath("$['institutions'][0].webSiteUrl").value(DB_WEBSITE));
    }
    
    @Test
    public void testGetInstitution() throws Exception {
    	mockMvc.perform(get(URL_PREFIX + "/" + DB_ID))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(contentType))
    	.andExpect(jsonPath("$.id").value(DB_ID))
    	.andExpect(jsonPath("$.name").value(DB_NAME))
        .andExpect(jsonPath("$.location").value(DB_LOCATION))
        .andExpect(jsonPath("$.director").value(DB_DIRECTOR))
        .andExpect(jsonPath("$.email").value(DB_EMAIL))
        .andExpect(jsonPath("$.contactPhone").value(DB_PHONE_NUMBER))
        .andExpect(jsonPath("$.webSiteUrl").value(DB_WEBSITE));
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteInstitution() throws Exception { 	
        this.mockMvc.perform(delete(URL_PREFIX + "/" + DB_ID))
                .andExpect(status().isOk());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testSaveInstitution() throws Exception {
    	Institution institution = new Institution();
    	institution.setName(NEW_NAME);
		institution.setLocation(NEW_LOCATION);
		institution.setDirector(NEW_DIRECTOR);
		institution.setEmail(NEW_EMAIL);
		institution.setContactPhone(NEW_PHONE_NUMBER);
		institution.setWebSiteUrl(NEW_WEBSITE);
    	
    	String json = TestUtil.json(institution);
        this.mockMvc.perform(post(URL_PREFIX)
                .contentType(contentType)
                .content(json))
                .andExpect(status().isCreated());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateInstitution() throws Exception {
    	Institution institution = new Institution();
    	institution.setName(NEW_NAME);
		institution.setLocation(NEW_LOCATION);
		institution.setDirector(NEW_DIRECTOR);
		institution.setEmail(NEW_EMAIL);
		institution.setContactPhone(NEW_PHONE_NUMBER);
		institution.setWebSiteUrl(NEW_WEBSITE);
    	
    	String json = TestUtil.json(institution);
        this.mockMvc.perform(put(URL_PREFIX)
                .contentType(contentType)
                .content(json))
                .andExpect(status().isOk());
    }
}
