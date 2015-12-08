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
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={CBioEngine.class})
public class MongoTests {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Before
	public void setUp() {
		
		mongoTemplate.dropCollection("clinical");
		
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
        attributes.add(new ClinicalTuple("DFS_MONTHS", 30));

        // save the clinical record.        
		mongoTemplate.insert(cr, "clinical");
	}
	
	@After
	public void tearDown() {
		
	}

	@Test
	public void testBasicQuery() {
		
		Criteria attributeFilter = Criteria.where("key").is("AGE").and("value").gte(40);
		Criteria ageFilter = Criteria.where("attributes").elemMatch(attributeFilter);
		Query ageQuery = Query.query(ageFilter);
		List<Object> result = mongoTemplate.find(ageQuery, Object.class, "clinical");
		
		assertEquals(1, result.size());
	}

	@Test
	public void testBasicQueryMiss() {
		
		Criteria attributeFilter = Criteria.where("key").is("AGE").and("value").gte(60);
		Criteria ageFilter = Criteria.where("attributes").elemMatch(attributeFilter);
		Query ageQuery = Query.query(ageFilter);
		List<Object> result = mongoTemplate.find(ageQuery, Object.class, "clinical");
		
		assertEquals(0, result.size());
	}

	@Test
	public void testBasicQueryString() {
		
		Query ageQuery = new BasicQuery("{\"attributes\": {\"$elemMatch\": {\"key\": \"AGE\", \"value\": {\"$gte\": 40}}}}");
		List<Object> result = mongoTemplate.find(ageQuery, Object.class, "clinical");
		
		assertEquals(1, result.size());		
	}
	
	@Test
	public void testAggregationQuery() {
		
//      // Adapted from this command line equivalent
//		db.runCommand(
//				  {aggregate: "clinical",
//				   pipeline: [
//				     { $match: { attributes: {$elemMatch: {key: "OS_MONTHS", value: {$ne: null} } }}},
//				     { $project : { "attributes" : 1 }},
//				     { $unwind : "$attributes" },
//				     { $match: { "attributes.key" : "OS_MONTHS" }},
//				     { $match: { "attributes.value" : { $gte : 0 }}},
//				     { $project : { "value" : "$attributes.value"}},
//				     { $group : { _id : "$value", count : { $sum : 1 }}},
//				     { $sort : { "_id" : -1 }}
//				   ]});
//		
		Aggregation agg = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("attributes").elemMatch(Criteria.where("key").is("AGE").and("value").gte(40))),
				Aggregation.project("attributes"),
				Aggregation.unwind("attributes"),
				Aggregation.match(Criteria.where("attributes.key").is("OS_MONTHS")),
				Aggregation.match(Criteria.where("attributes.value").gte(0)),
				Aggregation.project(Fields.from(Fields.field("value", "attributes.value")))
		);
	
		AggregationResults<Object> aggro = mongoTemplate.aggregate(agg, "clinical", Object.class);
		List<Object> result = aggro.getMappedResults();
		assertEquals(1, result.size());		
	}
}
