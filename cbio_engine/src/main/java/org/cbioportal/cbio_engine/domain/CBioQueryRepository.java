package org.cbioportal.cbio_engine.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by jlindsay on 12/8/15.
 */
@RepositoryRestResource(collectionResourceRel = "cbioquery", path = "cbioquery")
public interface CBioQueryRepository extends MongoRepository<CBioQuery, String> {

    List<CBioQuery> findByTransform(@Param("transform") String transform);
}
