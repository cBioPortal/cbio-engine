package org.cbioportal.cbio_engine.event;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cbioportal.cbio_engine.domain.CBioQuery;
import org.cbioportal.cbio_engine.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jlindsay on 12/8/15.
 */
@Component
@RepositoryEventHandler(CBioQuery.class)
public class CBioQueryEvent {

    // basic logger.
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private QueryService queryService;

    @HandleBeforeCreate
    public void handleBeforeCreate(CBioQuery cBioQuery){
        log.info("handleBeforeCreate event called");

        // execute clinical query.
        List<Object> result = queryService.execute(cBioQuery.getClinical_filter());

        // serializer.
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").create();

        // save to object.
        cBioQuery.setResults(gson.toJson(result, List.class));

        log.info("handleBeforeCreate even finished");
    }

    @HandleAfterCreate
    public void handleAfterCreate(CBioQuery cBioQuery) {
        log.info("handleAfterCreate event called");
        log.info("handleAfterCreate even finished");
    }
}
