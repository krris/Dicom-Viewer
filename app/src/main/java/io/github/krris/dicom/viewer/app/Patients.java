package io.github.krris.dicom.viewer.app;

import android.util.Log;

import java.io.File;
import java.util.*;


/**
 * Created by krris on 28.12.14.
 * Copyright (c) 2014 krris. All rights reserved.
 */
public class Patients {
    private static Patients ourInstance = new Patients();
    private Map<String,Patient> patients;

    public static Patients getInstance() {
        return ourInstance;
    }

    private Patients() {
        this.patients = new HashMap<>();
    }

    public Map<String, Patient> getAllPatients() {
        return this.patients;
    }

    public Patient getPatient(String name) {
        return this.patients.get(name);
    }

    private boolean patientExists(String name) {
        return this.patients.containsKey(name);
    }

    public void addOneImage(String path) {
        DicomData dicomData = new DicomData(path);

        String patientName = dicomData.getPatientName();
        if (this.patientExists(patientName)) {
            this.patients.get(patientName).addOneImage(dicomData);
        } else {
            Patient patient = new Patient(patientName);
            patient.addOneImage(dicomData);
            this.patients.put(patientName, patient);
        }
    }

    public void addPatient(String path) {
        addOneImage(path);




//        File file = new File(path);
//        if (file.getName().endsWith(".dcm")) {
//            file = file.getParentFile().getParentFile();
//        }
//
//        String patientName = file.getName();
//        Log.i("Patient name", patientName);
//        Patient patient = new Patient(patientName);
//
//        File[] medicalTestsDirs = file.listFiles();
//        for (File testDir : medicalTestsDirs) {
//            String testName = testDir.getName();
//            Log.i("Test name", testName);
//
//            MedicalTest test = new MedicalTest(testName);
//            File[] images = testDir.listFiles();
//            for(File image: images) {
//                Log.i("Images", image.getAbsolutePath());
//            }
//
//            test.setPathsToImages(images);
//            patient.addMedicalTest(test);
//        }
//
//        this.patients.add(patient);
    }
}
