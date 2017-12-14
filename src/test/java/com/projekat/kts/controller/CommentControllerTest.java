package com.projekat.kts.controller;

import static com.projekat.kts.constants.CommentConstants.DB_COUNT_COMMENT_BY_FAILURE;
import static com.projekat.kts.constants.CommentConstants.DB_ID_FAILURE;
import static com.projekat.kts.constants.CommentConstants.DB_TEXT;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentControllerTest {

private static final String URL_PREFIX = "/api/komentar";
	
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
    public void testGetAllComments() throws Exception {
    	mockMvc.perform(get(URL_PREFIX + '/' + DB_ID_FAILURE))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$", hasSize(DB_COUNT_COMMENT_BY_FAILURE)))
	        .andExpect(jsonPath("$[0].text").value(DB_TEXT));
    }
}
