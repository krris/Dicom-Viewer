package io.github.krris.dicom.viewer.app;

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
        for (int i = 0; i < 10; i++) {
            Patient patient = new Patient();
            patient.setName("Pacjent nr: " + i);
            patient.addMedicalTest(new MedicalTest());
            this.patients.add(patient);
        }
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
}
