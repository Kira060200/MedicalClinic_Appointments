package service;

import model.*;

import javax.print.Doc;

public class ClinicalManagement {

    public void addStaff(MedicalClinic clinic, MedicalStaff staff) {
        int nextAvailableIndex = getNumberOfStaff(clinic);
        clinic.getStaff()[nextAvailableIndex] = staff;
    }

    public void addPatient(MedicalClinic clinic, Patient patient) {
        int nextAvailableIndex = getNumberOfPatients(clinic);
        clinic.getPatients()[nextAvailableIndex] = patient;
    }

    public void addAppointment(MedicalClinic clinic, Appointment app) {
        int nextAvailableIndex = getNumberOfAppointments(clinic);
        clinic.getAppointments()[nextAvailableIndex] = app;
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

    public void printAppointmentsDetails(MedicalClinic clinic) {
        for(Appointment a : clinic.getAppointments()) {
            if(a != null) {
                System.out.println(a + "\n");
            }
        }
    }

    private int getNumberOfAppointments(MedicalClinic clinic) {
        int numberOfAppointments = 0;
        for(Appointment a : clinic.getAppointments()) {
            if(a != null) {
                numberOfAppointments++;
            }
        }
        return numberOfAppointments;
    }

    public Patient searchPatient(MedicalClinic mc, String firstName, String lastName) {
        for (Patient pat : mc.getPatients())
            if (pat != null)
                if (pat.getFirstName().equals(firstName) && pat.getLastName().equals(lastName))
                    return pat;
        return null;
    }

    public Doctor searchDoctor(MedicalClinic mc, String firstName, String lastName) {
        for (MedicalStaff doc : mc.getStaff())
            if (doc instanceof Doctor)
                if (doc.getFirstName().equals(firstName) && doc.getLastName().equals(lastName))
                    return (Doctor) doc;
        return null;
    }

    public Assistant searchAssistant(MedicalClinic mc, String firstName, String lastName) {
        for (MedicalStaff as : mc.getStaff())
            if (as instanceof Assistant)
                if (as.getFirstName().equals(firstName) && as.getLastName().equals(lastName))
                    return (Assistant) as;
        return null;
    }

    public void showStatsAppointments(MedicalClinic clinic) {
        int numberOfConsultations= 0;
        int numberOfSurgeries= 0;
        for(Appointment a : clinic.getAppointments()) {
            if(a instanceof MedicalConsultation) {
                numberOfConsultations++;
            }
            if(a instanceof MedicalSurgery) {
                numberOfSurgeries++;
            }
        }
        System.out.println("In the database, there are:");
        System.out.println(numberOfConsultations + " consultations");
        System.out.println(numberOfSurgeries + " surgeries");
    }

    public void showStatsStaff(MedicalClinic clinic) {
        int numberOfAssistants = 0;
        int numberOfDoctors = 0;
        for(MedicalStaff p : clinic.getStaff()) {
            if(p instanceof Assistant) {
                numberOfAssistants++;
            }
            if(p instanceof Doctor) {
                numberOfDoctors++;
            }
        }
        System.out.println("In the database, there are:");
        System.out.println(numberOfAssistants + " assistants");
        System.out.println(numberOfDoctors + " doctors");
    }

    public void showOverview(MedicalClinic clinic){
        System.out.println("The database has the following records:");
        System.out.println("Medical staff: " + getNumberOfStaff(clinic));
        System.out.println("Patients: " + getNumberOfPatients(clinic));
        System.out.println("Appointments: " + getNumberOfAppointments(clinic));
    }

    public void removePatient(MedicalClinic mc, String firstName, String lastName) {
        int i = 0;
        for (Patient pat : mc.getPatients()) {
            if (pat != null)
                if (pat.getFirstName().equals(firstName) && pat.getLastName().equals(lastName))
                    mc.deletePatient(i);
            i++;
        }
    }

    public void removeStaff(MedicalClinic mc, String firstName, String lastName) {
        int i = 0;
        for (MedicalStaff staff : mc.getStaff()) {
            if (staff != null)
                if (staff.getFirstName().equals(firstName) && staff.getLastName().equals(lastName))
                    mc.deleteStaff(i);
            i++;
        }
    }

    public void removeAppointment(MedicalClinic mc, String date, String docFirstName, String docLastName, String patFirstName, String patLastName) {
        int i = 0;
        for (Appointment a : mc.getAppointments()) {
            if (a != null)
                if (a.getDate().equals(date) && a.getDoc().getFirstName().equals(docFirstName) && a.getDoc().getLastName().equals(docLastName) && a.getPat().getFirstName().equals(patFirstName) && a.getPat().getLastName().equals(patLastName))
                    mc.deleteAppointment(i);
            i++;
        }
    }
}
