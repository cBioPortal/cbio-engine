package org.cbioportal.cbio_engine.event;

import org.cbioportal.cbio_engine.domain.CBioQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

/**
 * Created by jlindsay on 12/8/15.
 */
@Component
@RepositoryEventHandler(CBioQuery.class)
public class CBioQueryEvent {

    // basic logger.
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @HandleBeforeCreate
    public void handleBeforeCreate(CBioQuery cBioQuery){
        log.info("handleBeforeCreate event called");
        cBioQuery.setResults("{\"hello\": \"kitty\"}");
        log.info("handleBeforeCreate even finished");
    }

    @HandleAfterCreate
    public void handleAfterCreate(CBioQuery cBioQuery) {
        log.info("handleAfterCreate event called");
        log.info("handleAfterCreate even finished");
    }
}
