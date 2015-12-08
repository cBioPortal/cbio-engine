package org.cbioportal.cbio_engine.service.internal;

import java.util.List;

import org.cbioportal.cbio_engine.query.BasicQueryFilter;
import org.cbioportal.cbio_engine.query.Query;
import org.cbioportal.cbio_engine.query.QueryFilter;
import org.cbioportal.cbio_engine.query.QueryPartition;
import org.cbioportal.cbio_engine.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Implements the basic query service for MongoDB. In this implementation,
 * the query filters are used to select documents from both the genomic and
 * clinical collections, and then we merge them here at runtime, depending
 * on the ordering. The actual execution of the query might vary, as might
 * the transform that's applied to the results.
 * 
 * @author stuartw
 */
@Service
public class MongoQueryService implements QueryService {
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<ObjectNode> execute(Query query) {
						
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query newQuery() {
		return new MongoQuery();
	}

	@Override
	public Query setQueryFilter(Query input, QueryFilter filter) {
		MongoQuery query = getAsMongoQuery(input);
		query.setQueryFilter(filter);
		return query;
	}

	@Override
	public Query setQueryPartition(Query input, List<QueryPartition> partitions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query setQueryTransform(Query input) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private MongoQuery getAsMongoQuery(Query query) {
		if (query instanceof MongoQuery) {
			return (MongoQuery) query;
		} else {
			throw new RuntimeException("Invalid query: " + query.toString());
		}
	}

	private class MongoQuery implements Query {
		
		private Criteria criteria;
		
		MongoQuery() {
			criteria = new Criteria();
		}
		
		private void setQueryFilter(QueryFilter filter) {
			if (filter instanceof BasicQueryFilter) {
				setBasicQueryFilter((BasicQueryFilter) filter);
			} else {
				throw new RuntimeException("Invalid query filter type: " + filter);
			}
		}
		
		private void setBasicQueryFilter(BasicQueryFilter filter) {
			
		}
		
		
	}
}
