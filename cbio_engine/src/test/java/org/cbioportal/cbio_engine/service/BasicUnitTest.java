package unit;

import org.cbioportal.cbio_engine.CBioEngine;
import org.cbioportal.cbio_engine.domain.ClinicalRecordRepository;
import org.cbioportal.cbio_engine.domain.GenomicRecordRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

import org.cbioportal.cbio_engine.service.CsvService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jlindsay on 12/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={CBioEngine.class})
public class BasicUnitTest {

    @Autowired
    private CsvService csvService;

    @Autowired
    private GenomicRecordRepository genomicRecordRepository;

    @Autowired
    private ClinicalRecordRepository clinicalRecordRepository;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {
        clinicalRecordRepository.deleteAll();
        genomicRecordRepository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        clinicalRecordRepository.deleteAll();
        genomicRecordRepository.deleteAll();
    }

    @Test
    public void hellowWorld(){
        assertEquals(1, 1);
    }

    @Ignore
    public void csvLoader() throws IOException {

        // get the path.
        String path = new File(".").getCanonicalPath() + "/src/main/resources/data/";

        // prefix to import.
        List<String> prefix = new ArrayList<>(Arrays.asList(
                "acc",
                "blca",
                "brca",
                "cesc",
                "chol",
                "coadread",
                "dlbc",
                "esca",
                "gbm",
                "hnsc",
                "kich",
                "kirc",
                "kirp",
                "laml",
                "lgg",
                "lihc",
                "luad",
                "lusc",
                "meso",
                "ov",
                "paad",
                "pcpg",
                "prad",
                "sarc",
                "skcm",
                "stad",
                "tgct",
                "thca",
                "thym",
                "ucec",
                "ucs",
                "uvm"));

        // list of paths to import.
        List<HashMap<String, String>> paths = new ArrayList<>();
        for(String p: prefix){

            // build dict.
            HashMap<String, String> tmp = new HashMap<>();
            tmp.put("prefix", p);
            tmp.put("path", path + p + "/tcga/data_bcr_clinical_data.txt");

            // add to list.
            paths.add(tmp);
        }

        // import it.
        csvService.importPatientTcga(paths);

        // assert we have more than 3000.
        Long count = clinicalRecordRepository.count();
        assertTrue((count > 2000L));

    }

}