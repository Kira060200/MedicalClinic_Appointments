package service;

import model.*;

public class ClinicalManagement {
    public void addStaff(MedicalClinic clinic, MedicalStaff staff) {
        clinic.getStaff()[clinic.getStaff().length] = staff;
    }
    public void addPatient(MedicalClinic clinic, Patient patient) {
        clinic.getPatients()[clinic.getPatients().length] = patient;
    }
}
