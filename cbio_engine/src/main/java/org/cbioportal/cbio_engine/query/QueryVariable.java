package org.cbioportal.cbio_engine.query;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class QueryVariable {
	
	/**
	 * Scope for clinical variables.
	 */
	public static final String CLINICAL_SCOPE = "clinical";
		
	/**
	 * Scope for genomic variables.
	 */
	public static final String GENOMIC_SCOPE = "genomic";

	/**
	 * The variable scope: ie, the same variable name might
	 * just occur in a different scope.
	 */
	@JsonProperty
	private String scope;

	/**
	 * The variable name. It's a string.
	 */
	@JsonProperty
	private String name;
	
	/**
	 * Constructor for a query variable.
	 * @param scope
	 * @param name
	 */
	public QueryVariable(String scope, String name) {
		this.scope = scope;
		this.name = name;
	}
}
