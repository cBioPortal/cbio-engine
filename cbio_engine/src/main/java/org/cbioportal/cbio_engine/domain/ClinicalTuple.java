package org.cbioportal.cbio_engine.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

/**
 * Created by jlindsay on 12/7/15.
 */
public class ClinicalTuple {
	
	private static final JsonNodeFactory factory = JsonNodeFactory.instance;
	
    public String key;
    
    public JsonNode value;

    public ClinicalTuple(String key, JsonNode value) {
        this.key = key;
        this.value = value;
    }
    
    /**
     * Constructor for strings.
     * @param key
     * @param value
     */
    public ClinicalTuple(String key, String value) {
    	this(key, factory.textNode(value));
    }

    /**
     * Constructor for numbers.
     * @param key
     * @param value
     */
    public ClinicalTuple(String key, Integer value) {
    	this(key, factory.numberNode(value));
    }
}
