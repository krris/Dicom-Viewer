package io.github.krris.dicom.viewer.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krris on 28.12.14.
 * Copyright (c) 2014 krris. All rights reserved.
 */
public class Patient {
    private List<MedicalTest> medicalTests = new ArrayList<>();

    public void addMedicalTest(MedicalTest test) {
        this.medicalTests.add(test);
    }

    public List<MedicalTest> getAllMedicalTests() {
        return this.medicalTests;
    }
}
