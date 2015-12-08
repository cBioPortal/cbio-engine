package org.cbioportal.cbio_engine.service.internal;

import static org.junit.Assert.*;

import org.cbioportal.cbio_engine.CBioEngine;
import org.cbioportal.cbio_engine.query.Query;
import org.cbioportal.cbio_engine.service.QueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={CBioEngine.class})
public class MongoQueryServiceTest {
	
	@Autowired
	QueryService service;
	
	@Test
	public void testNewQuery() {
		
		Query query = service.newQuery();
		assertNotNull(query);
	}

}
