package org.cbioportal.cbio_engine;

import org.cbioportal.cbio_engine.service.CsvService;
import org.cbioportal.cbio_engine.service.GsvService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jlindsay on 12/8/15.
 */
public class ProductionHack {

    @Autowired
    private CsvService csvService;

    @Autowired
    private GsvService gsvService;

    public void loadData() throws IOException {
        csvLoader();
        gsvLoader();
    }

    public void gsvLoader() throws IOException {

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
            tmp.put("path", path + p + "/tcga/data_mutations_extended.txt");

            // add to list.
            paths.add(tmp);
        }

        // import it.
        gsvService.importGenomicTcga(paths);

        // assert we have more than 3000.
        //Long count = genomicRecordRepository.count();
        //assertTrue((count > 2000L));

    }
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
    }
}
