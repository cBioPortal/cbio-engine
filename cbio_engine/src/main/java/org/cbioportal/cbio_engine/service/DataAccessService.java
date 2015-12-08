package org.cbioportal.cbio_engine.service;

import org.cbioportal.cbio_engine.domain.*;
import org.cbioportal.cbio_engine.service.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

@Service
public class DataAccessService
{
    private final ClinicalRecordRepository clinicalRecordRepository;
    private final GenomicRecordRepository genomicRecordRepository;

    @Autowired
    public DataAccessService(ClinicalRecordRepository clinicalRecordRepository,
                             GenomicRecordRepository genomicRecordRepository)
    {
        this.clinicalRecordRepository = clinicalRecordRepository;
        this.genomicRecordRepository = genomicRecordRepository;
    }

    public ClinicalRecord getClinicalRecordBySampleId(String sampleId)
    {
        return clinicalRecordRepository.findOne(sampleId);
    }
}
