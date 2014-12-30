package io.github.krris.dicom.viewer.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krris on 28.12.14.
 * Copyright (c) 2014 krris. All rights reserved.
 */
public class Patient {
    private String name;
    private List<MedicalTest> medicalTests = new ArrayList<>();

    public Patient(String patientName) {
        this.name = patientName;
    }

    public void addMedicalTest(MedicalTest test) {
        this.medicalTests.add(test);
    }

    public List<MedicalTest> getAllMedicalTests() {
        return this.medicalTests;
    }

    public MedicalTest getMedicalTest(String medicalTestName) {
        for (MedicalTest test : medicalTests) {
            if (medicalTestName.equals(test.getName())) {
                return test;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
