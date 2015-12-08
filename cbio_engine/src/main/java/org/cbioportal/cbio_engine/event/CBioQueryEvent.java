package org.cbioportal.cbio_engine.event;

import org.cbioportal.cbio_engine.domain.CBioQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
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

    @HandleAfterCreate
    public void handleAfterCreate(CBioQuery cBioQuery) {

        // log we hit event.
        log.info("handleAfterCreate event called");

        //cBioQuery.

        log.info("handleAfterCreate even finished");
    }
}
