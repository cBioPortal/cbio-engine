package org.cbioportal.cbio_engine;

import org.cbioportal.cbio_engine.domain.CBioQuery;
import org.cbioportal.cbio_engine.domain.ClinicalRecord;
import org.cbioportal.cbio_engine.domain.GenomicRecord;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 * Created by jlindsay on 12/8/15.
 */

@Configuration
@EnableMongoRepositories
public class SdrConfig extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        super.configureRepositoryRestConfiguration(config);

        // set a return body.
        config.setReturnBodyOnCreate(true);

        // make sure we expose the id.
        config.exposeIdsFor(CBioQuery.class);
        config.exposeIdsFor(GenomicRecord.class);
        config.exposeIdsFor(ClinicalRecord.class);
    }
}

