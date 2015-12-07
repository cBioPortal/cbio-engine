package org.cbioportal.cbio_engine.service;

import java.util.List;

import org.cbioportal.cbio_engine.query.Query;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface QueryService {

	public List<ObjectNode> execute(Query query);
}
