package io.github.krris.dicom.viewer.app;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by krris on 28.12.14.
 * Copyright (c) 2014 krris. All rights reserved.
 */
public class Patients {
    private static Patients ourInstance = new Patients();
    private List<Patient> patients;

    public static Patients getInstance() {
        return ourInstance;
    }

    private Patients() {
        this.patients = new ArrayList<>();
    }

    public List<Patient> getAllPatients() {
        return this.patients;
    }

    public Patient getPatient(String name) {
        for (Patient patient : patients) {
            if (patient.getName().equals(name)) {
                return patient;
            }
        }
        return null;
    }

    public void addPatient(String path) {
        // jesli zaznaczono jedno zdjecje dcm => otwieramy i doda





        File file = new File(path);
        if (file.getName().endsWith(".dcm")) {
            file = file.getParentFile().getParentFile();
        }

        String patientName = file.getName();
        Log.i("Patient name", patientName);
        Patient patient = new Patient(patientName);

        File[] medicalTestsDirs = file.listFiles();
        for (File testDir : medicalTestsDirs) {
            String testName = testDir.getName();
            Log.i("Test name", testName);

            MedicalTest test = new MedicalTest(testName);
            File[] images = testDir.listFiles();
            for(File image: images) {
                Log.i("Images", image.getAbsolutePath());
            }

            test.setPathsToImages(images);
            patient.addMedicalTest(test);
        }

        this.patients.add(patient);
    }
}
