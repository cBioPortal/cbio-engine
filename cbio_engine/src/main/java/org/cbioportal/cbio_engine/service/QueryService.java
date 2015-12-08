package org.cbioportal.cbio_engine.service;

import java.util.List;

import org.cbioportal.cbio_engine.query.Query;
import org.cbioportal.cbio_engine.query.QueryFilter;
import org.cbioportal.cbio_engine.query.QueryPartition;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface QueryService {
	
	public Query newQuery();
	
	public Query setQueryFilter(Query input, QueryFilter filter);
	
	public Query setQueryPartition(Query input, List<QueryPartition> partitions);
	
	public Query setQueryTransform(Query input);

	public List<ObjectNode> execute(Query query);
}
