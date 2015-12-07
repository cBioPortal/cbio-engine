package unit;

import org.cbioportal.cbio_engine.CBioEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertEquals;

import org.cbioportal.cbio_engine.service.CsvService;

import java.io.File;
import java.io.IOException;

/**
 * Created by jlindsay on 12/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={CBioEngine.class})
public class BasicUnitTest {

    @Autowired
    private CsvService csvService;

    @Test
    public void hellowWorld(){
        assertEquals(1, 1);
    }

    @Test
    public void csvLoader(){

        // get the path.
        String path = null;
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // import it.
        csvService.importPatientTcga(path + "/src/main/resources/data/data_bcr_clinical_data.csv");

    }

}