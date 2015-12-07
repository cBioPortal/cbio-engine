package org.cbioportal.cbio_engine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by jlindsay on 12/7/15.
 */
@Service
public class CsvService {

    // basic logger.
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());


    /**
     * imports patient and clinical data from properties file
     * @param path: path to the properties file.
     */
    public void importPatientTcga(String path) {

        // file reading variables.
        File file = new File(path);
        BufferedReader reader = null;

        log.info("importing paitents from file...");
        try {

            // create reader.
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            String[] values;

            // iterator over each line.
            int i = 0;
            while ((text = reader.readLine()) != null) {

                // skip header.
                values = text.split("\\t", -1);

                // TODO: write parser.

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

        // save all patients in one go.
        log.info("importing paitents from file...done");
    }
}
