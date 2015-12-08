package org.cbioportal.cbio_engine.service.internal;

import static org.junit.Assert.*;

import java.util.List;

import org.cbioportal.cbio_engine.CBioEngine;
import org.cbioportal.cbio_engine.domain.ClinicalRecord;
import org.cbioportal.cbio_engine.domain.ClinicalTuple;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={CBioEngine.class})
public class MongoTests {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Before
	public void setUp() {
		
		String sampleId = "sample";
		String patientId = "patient";
		String cancerId = "cancer";
		String cancerType = "carcinoma";
		
        ClinicalRecord cr = new ClinicalRecord(sampleId, patientId, cancerId, cancerType);
        List<ClinicalTuple> attributes = cr.getAttributes();
        attributes.add(new ClinicalTuple("AGE", 50));
        attributes.add(new ClinicalTuple("OS_STATUS", "LIVING"));
        attributes.add(new ClinicalTuple("OS_MONTHS", 32));
        attributes.add(new ClinicalTuple("DFS_STATUS", "REMISSION"));
        attributes.add(new ClinicalTuple("DFS_MONTHS", 32));

        // save the clinical record.        
		mongoTemplate.insert(cr, "clinical");
	}
	
	@After
	public void tearDown() {
		
	}

	@Test
	public void testBasicQuery() {
		
		Criteria ageFilter = Criteria.where("age").gt(10);
		Query ageQuery = Query.query(ageFilter);
		List<ObjectNode> result = mongoTemplate.find(ageQuery, ObjectNode.class, "clinical");
		
		assertEquals(1, result.size());
	}

}