package service;

import model.*;

public class ClinicalManagement {
    public void addStaff(MedicalClinic clinic, MedicalStaff staff) {
        int nextAvailableIndex = getNumberOfStaff(clinic);
        clinic.getStaff()[nextAvailableIndex] = staff;
    }

    public void addPatient(MedicalClinic clinic, Patient patient) {
        int nextAvailableIndex = getNumberOfPatients(clinic);
        clinic.getPatients()[nextAvailableIndex] = patient;
    }

    public void printStaffDetails(MedicalClinic clinic) {
        for(MedicalStaff p : clinic.getStaff()) {
            if(p != null) {
                System.out.println(p);
            }
        }
    }

    private int getNumberOfStaff(MedicalClinic clinic) {
        int numberOfStaff = 0;
        for(MedicalStaff p : clinic.getStaff()) {
            if(p != null) {
                numberOfStaff++;
            }
        }
        return numberOfStaff;
    }

    public void printPatientsDetails(MedicalClinic clinic) {
        for(Patient p : clinic.getPatients()) {
            if(p != null) {
                System.out.println(p);
            }
        }
    }

    private int getNumberOfPatients(MedicalClinic clinic) {
        int numberOfPatients = 0;
        for(Patient p : clinic.getPatients()) {
            if(p != null) {
                numberOfPatients++;
            }
        }
        return numberOfPatients;
    }
}
