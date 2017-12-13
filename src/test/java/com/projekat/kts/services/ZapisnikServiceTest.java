package com.projekat.kts.services;

import static com.projekat.kts.constants.ZapisnikConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.projekat.kts.model.Zapisnik;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZapisnikServiceTest {

	@Autowired
	private ZapisnikService zapisnikService;
	
	@Autowired
	private SednicaService sednicaService;
	
	@Test
	public void testBySednica() {
		List<Zapisnik> zapisnici = zapisnikService.findBySednica(sednicaService.findById(DB_ID_SEDNICA));
		assertThat(zapisnici).hasSize(DB_COUNT_ZAPISNIK_BY_SEDNICA); 
	}
}
