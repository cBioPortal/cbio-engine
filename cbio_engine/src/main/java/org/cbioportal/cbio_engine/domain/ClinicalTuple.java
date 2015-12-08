package org.cbioportal.cbio_engine.domain;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by jlindsay on 12/7/15.
 */
public class ClinicalTuple {
		
	@Field
    public String key;
    
    @Field
    public Object value;

    public ClinicalTuple(String key, Object value) {
        this.key = key;
        this.value = value;
    }
    
}
