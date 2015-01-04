package io.github.krris.dicom.viewer.app;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by krris on 28.12.14.
 * Copyright (c) 2014 krris. All rights reserved.
 */
public class Patient {
    private String name;
    private Map<String, MedicalTest> medicalTests = new HashMap<>();

    public Patient(String patientName) {
        this.name = patientName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addOneImage(DicomData dicomData) {
        String medicalTestName = dicomData.getMedicalTestName();
        if (medicalTestExist(medicalTestName)) {
            this.medicalTests.get(medicalTestName).addOneImage(dicomData);
        } else {
            MedicalTest medicalTest = new MedicalTest(medicalTestName);
            medicalTest.addOneImage(dicomData);
            this.medicalTests.put(medicalTestName, medicalTest);
        }
    }

    private boolean medicalTestExist(String medicalTestName) {
        return this.medicalTests.containsKey(medicalTestName);
    }

    public Map<String, MedicalTest> getMedicalTests() {
        return medicalTests;
    }


    public Map<String, Series> getAllSeries(String medicalTestName) {
        return medicalTests.get(medicalTestName).getAllSeries();
    }

    public MedicalTest getMedicalTest(String medicalTestName) {
        return this.medicalTests.get(medicalTestName);
    }
}
