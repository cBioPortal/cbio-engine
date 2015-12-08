package org.cbioportal.cbio_engine.query;

import java.util.List;

public class AndQueryFilter {
	
	public AndQueryFilter(List<QueryFilter> conditions) {
		super();
		this.conditions = conditions;
	}

	private List<QueryFilter> conditions;

	/**
	 * @return the conditions
	 */
	public List<QueryFilter> getConditions() {
		return conditions;
	}

	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(List<QueryFilter> conditions) {
		this.conditions = conditions;
	}
}
