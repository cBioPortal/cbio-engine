package org.cbioportal.cbio_engine.service;

import org.cbioportal.cbio_engine.domain.ClinicalRecord;
import org.cbioportal.cbio_engine.domain.ClinicalRecordRepository;
import org.cbioportal.cbio_engine.domain.ClinicalTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * Created by jlindsay on 12/7/15.
 */
@Service
public class CsvService {

    // basic logger.
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private ClinicalRecordRepository clinicalRecordRepository;

    /**
     * imports patient and clinical data from properties file
     * @param paths: List of prefix/path hashmap
     */
    public void importPatientTcga(List<HashMap<String, String>> paths) {
        log.info("importing paitents from file...");

        // create base object.
        File file;
        BufferedReader reader = null;

        // loop over each entry.
        for(HashMap<String, String> entry: paths){

            // simplify.
            String path = entry.get("path");
            String prefix = entry.get("prefix");

            // file reading variables.
            file = new File(path);
            reader = null;

            // test if file exists.
            if(!file.exists()){
                continue;
            }

            // list to store records per project.
            List<ClinicalRecord> records = new ArrayList<>();

            try {

                // create reader.
                reader = new BufferedReader(new FileReader(file));
                String text = null;
                String[] values;

                String patient_id;
                String sample_id;
                String os_status;
                String os_months;
                String dfs_status;
                String dfs_months;
                String age;
                String cancer_id;
                String cancer_type;

                // keys to look for.
                Set<String> keys = new HashSet<String>(Arrays.asList(
                        "PATIENT_ID", "SAMPLE_ID", "OS_STATUS", "OS_MONTHS", "DFS_STATUS", "DFS_MONTHS", "AGE"));

                // parse header and build lookup.
                HashMap<String, Integer> columnLu = new HashMap<>();
                values = reader.readLine().split("\\t", -1);
                int i = 0;
                for(String s: values){
                    if(keys.contains(s)){
                        columnLu.put(s, i);
                    }
                    i++;
                }

                // iterator over each line.
                i = 0;
                while ((text = reader.readLine()) != null) {

                    // skip header.
                    values = text.split("\\t", -1);

                    // parse out attribes.
                    patient_id = values[columnLu.get("PATIENT_ID")];
                    sample_id = values[columnLu.get("SAMPLE_ID")];
                    age = values[columnLu.get("AGE")];
                    cancer_type = prefix;
                    cancer_id = prefix + "_tcga";
                    os_status = values[columnLu.get("OS_STATUS")];
                    os_months = values[columnLu.get("OS_MONTHS")];
                    dfs_status = values[columnLu.get("DFS_STATUS")];
                    dfs_months = values[columnLu.get("DFS_MONTHS")];

                    // create the clinical record.
                    ClinicalRecord cr = new ClinicalRecord(sample_id, patient_id, cancer_id, cancer_type);
                    List<ClinicalTuple> attributes = cr.getAttributes();
                    attributes.add(new ClinicalTuple("AGE", age));
                    attributes.add(new ClinicalTuple("OS_STATUS", os_status));
                    attributes.add(new ClinicalTuple("OS_MONTHS", os_months));
                    attributes.add(new ClinicalTuple("DFS_STATUS", dfs_status));
                    attributes.add(new ClinicalTuple("DFS_MONTHS", dfs_months));

                    // save the clinical record.
                    records.add(cr);
                    i++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                }
            }

            // bulk insert.
            log.info("file: " + prefix +  ", count: " + Integer.toString(records.size()));
            clinicalRecordRepository.save(records);

        }



        // save all patients in one go.
        log.info("importing paitents from file...done");
    }
}
