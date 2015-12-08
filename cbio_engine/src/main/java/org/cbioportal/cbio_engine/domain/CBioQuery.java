package org.cbioportal.cbio_engine.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by jlindsay on 12/8/15.
 */
@Document(collection = "cbio_query")
public class CBioQuery {

    @Id
    private String cbioquery_id;

    private String genomic_filter;
    private String clinical_filter;
    private String transform;
    private String results;

    public String getGenomic_filter() {
        return genomic_filter;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getCbioquery_id() {
        return cbioquery_id;
    }

    public void setCbioquery_id(String cbioquery_id) {
        this.cbioquery_id = cbioquery_id;
    }

    public void setGenomic_filter(String genomic_filter) {
        this.genomic_filter = genomic_filter;
    }

    public String getClinical_filter() {
        return clinical_filter;
    }

    public void setClinical_filter(String clinical_filter) {
        this.clinical_filter = clinical_filter;
    }

    public String getTransform() {
        return transform;
    }

    public void setTransform(String transform) {
        this.transform = transform;
    }
}
