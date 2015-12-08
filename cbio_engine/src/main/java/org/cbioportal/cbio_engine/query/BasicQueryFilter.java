package org.cbioportal.cbio_engine.query;

import com.fasterxml.jackson.databind.JsonNode;

public class BasicQueryFilter implements QueryFilter {

	public BasicQueryFilter(QueryVariable variable, JsonNode value) {
		super();
		this.variable = variable;
		this.value = value;
	}

	private QueryVariable variable;
	
	private JsonNode value;

	/**
	 * @return the variable
	 */
	public QueryVariable getVariable() {
		return variable;
	}

	/**
	 * @param variable the variable to set
	 */
	public void setVariable(QueryVariable variable) {
		this.variable = variable;
	}

	/**
	 * @return the value
	 */
	public JsonNode getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(JsonNode value) {
		this.value = value;
	}
	
}
