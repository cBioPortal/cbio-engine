package unit;

import org.cbioportal.cbio_engine.cBioEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by jlindsay on 12/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={cBioEngine.class})
public class BasicUnitTest {

    @Test
    public void hellowWorld(){
        assertEquals(1, 1);
    }

}