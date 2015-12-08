package org.cbioportal.cbio_engine.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class BasicQueryPartition {

	@JsonProperty
	private QueryVariable variable;
	
	@JsonProperty
	private JsonNode value;

}
