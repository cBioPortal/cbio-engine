package org.cbioportal.cbio_engine.domain;

/**
 * Created by jlindsay on 12/7/15.
 */
public class ClinicalTuple {
    public String key;
    public String value;

    public ClinicalTuple(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
