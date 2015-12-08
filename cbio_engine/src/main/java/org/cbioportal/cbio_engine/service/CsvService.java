package org.cbioportal.cbio_engine.service;

import org.cbioportal.cbio_engine.domain.ClinicalRecord;
import org.cbioportal.cbio_engine.domain.ClinicalRecordRepository;
import org.cbioportal.cbio_engine.domain.ClinicalTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
     * @param path: path to the properties file.
     */
    public void importPatientTcga(String path) {

        // file reading variables.
        File file = new File(path);
        BufferedReader reader = null;

        List<ClinicalRecord> records = new ArrayList<>();

        log.info("importing paitents from file...");
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

            // iterator over each line.
            int i = 0;
            while ((text = reader.readLine()) != null) {

                // skip header.
                values = text.split("\\t", -1);

                // parse out attribes.
                patient_id = values[0];
                sample_id = values[110];
                age = values[20];
                cancer_type = "brca";
                cancer_id = "brca_tcga";
                os_status = values[106];
                os_months = values[107];
                dfs_status = values[108];
                dfs_months = values[109];

                //log.info(Integer.toString(i) + ", " + age + ", " + os_status);

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
        clinicalRecordRepository.save(records);

        // save all patients in one go.
        log.info("importing paitents from file...done");
    }
}
