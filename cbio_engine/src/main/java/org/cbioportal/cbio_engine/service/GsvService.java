package org.cbioportal.cbio_engine.service;

import org.cbioportal.cbio_engine.domain.GenomicRecord;
import org.cbioportal.cbio_engine.domain.GenomicRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Created by Benjamin Gross
 */
@Service
public class GsvService {

    // basic logger.
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    Pattern samplePattern = Pattern.compile("^(TCGA-\\w\\w-\\w\\w\\w\\w-\\d\\d).*$");

    @Autowired
    private GenomicRecordRepository genomicRecordRepository;

    /**
     * imports patient and clinical data from properties file
     * @param paths: List of prefix/path hashmap
     */
    public void importGenomicTcga(List<HashMap<String, String>> paths) {
        log.info("importing genomic records from file...");

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
            List<GenomicRecord> records = new ArrayList<>();

            try {

                // create reader.
                reader = new BufferedReader(new FileReader(file));
                String text = null;
                String[] values;

                String sample_id;
                String hugo_symbol;
                String variant;
                String variant_classification;


                // keys to look for.
                Set<String> keys = new HashSet<String>(Arrays.asList(
                        "Tumor_Sample_Barcode", "Hugo_Symbol", "HGVSp_Short", "Variant_Classification"));

                // skip header.
                reader.readLine();

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
                    sample_id = values[columnLu.get("Tumor_Sample_Barcode")];
                    Matcher matcher = samplePattern.matcher(sample_id);
                    sample_id = (matcher.find()) ? matcher.group(1) : sample_id;
                         
                    hugo_symbol = values[columnLu.get("Hugo_Symbol")];
                    variant = values[columnLu.get("HGVSp_Short")];
                    variant_classification = values[columnLu.get("Variant_Classification")];

                    // create genomic records
                    if (variant != null && variant.length() > 0) {
                        GenomicRecord gr = new GenomicRecord(sample_id, hugo_symbol, "VARIANT", variant);
                        records.add(gr);

                        //log.info(gr.getSampleId() + ", " + gr.getHugoSymbol());
                    }
                    if (variant != null && variant.length() > 0) {
                        GenomicRecord gr = new GenomicRecord(sample_id, hugo_symbol, "VARIANT_CLASSIFICATION", variant_classification);
                        records.add(gr);

                        //log.info(gr.getSampleId() + ", " + gr.getHugoSymbol());
                    }



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
            genomicRecordRepository.save(records);

        }



        // save all patients in one go.
        log.info("importing genomic records...done");
    }
}
