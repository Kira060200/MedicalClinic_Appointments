package service;

import model.*;

import java.util.Arrays;

public class ClinicalManagement {
    private final PersonService personService = new PersonService();
    private final StaffService staffService = new StaffService();
    private final AppointmentService appointmentService = new AppointmentService();
    private final LoggingService loggingService = new LoggingService();

    public void addStaff(MedicalClinic clinic, MedicalStaff staff) {
        clinic.getStaff().add(staff);
        if (staff instanceof Doctor){
            loggingService.logEvent("addDoctor");
        }
        else{
            loggingService.logEvent("addAssistant");
        }
    }

    public void addPatient(MedicalClinic clinic, Patient patient) {
        clinic.getPatients().add(patient);
        loggingService.logEvent("addPatient");
    }

    public void addAppointment(MedicalClinic clinic, Appointment app) {
        clinic.getAppointments().add(app);
        if (app instanceof MedicalConsultation){
            loggingService.logEvent("addConsultation");
        }
        else{
            loggingService.logEvent("addSurgery");
        }
    }

    public void printStaffDetails(MedicalClinic clinic) {
        for(MedicalStaff p : clinic.getStaff()) {
            if(p != null) {
                System.out.println(p);
            }
        }
        loggingService.logEvent("viewStaff");
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
        loggingService.logEvent("viewPatient");
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
        loggingService.logEvent("viewAppointment");
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

    public Patient searchPatient(MedicalClinic clinic, String firstName, String lastName) {
        for (Patient pat : clinic.getPatients())
            if (pat != null && pat.getFirstName() != null && pat.getLastName() != null)
                if (pat.getFirstName().equals(firstName) && pat.getLastName().equals(lastName))
                    return pat;
        return null;
    }

    public Doctor searchDoctor(MedicalClinic clinic, String firstName, String lastName) {
        for (MedicalStaff doc : clinic.getStaff())
            if (doc instanceof Doctor && doc.getFirstName() != null && doc.getLastName() != null)
                if (doc.getFirstName().equals(firstName) && doc.getLastName().equals(lastName))
                    return (Doctor) doc;
        return null;
    }

    public Assistant searchAssistant(MedicalClinic clinic, String firstName, String lastName) {
        for (MedicalStaff as : clinic.getStaff())
            if (as instanceof Assistant && as.getFirstName() != null && as.getLastName() != null)
                if (as.getFirstName().equals(firstName) && as.getLastName().equals(lastName))
                    return (Assistant) as;
        return null;
    }

    public MedicalStaff searchStaff(MedicalClinic clinic, String firstName, String lastName) {
        for (MedicalStaff staff : clinic.getStaff())
                if (staff != null && staff.getFirstName() != null && staff.getLastName() != null)
                    if(staff.getFirstName().equals(firstName) && staff.getLastName().equals(lastName))
                        return staff;
        return null;
    }

    public Appointment searchAppointment(MedicalClinic clinic, String date, String docFirstName, String docLastName, String patFirstName, String patLastName) {
        for (Appointment a : clinic.getAppointments())
            if (a != null && a.getDate() !=null && a.getDoc().getFirstName() != null && a.getDoc().getLastName() != null && a.getPat().getFirstName() != null && a.getPat().getLastName() != null)
                if (a.getDate().equals(date) && a.getDoc().getFirstName().equals(docFirstName) && a.getDoc().getLastName().equals(docLastName) && a.getPat().getFirstName().equals(patFirstName) && a.getPat().getLastName().equals(patLastName))
                    return a;
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
        loggingService.logEvent("statsAppointments");
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
        loggingService.logEvent("statsStaff");
        System.out.println("In the database, there are:");
        System.out.println(numberOfAssistants + " assistants");
        System.out.println(numberOfDoctors + " doctors");
    }

    public void showOverview(MedicalClinic clinic){
        loggingService.logEvent("showOverview");
        System.out.println("The database has the following records:");
        System.out.println("Medical staff: " + getNumberOfStaff(clinic));
        System.out.println("Patients: " + getNumberOfPatients(clinic));
        System.out.println("Appointments: " + getNumberOfAppointments(clinic));
    }

    public void removePatient(MedicalClinic clinic, String firstName, String lastName) {
        for (Patient pat : clinic.getPatients()) {
            if (pat != null && pat.getFirstName() != null && pat.getLastName() != null)
                if (pat.getFirstName().equals(firstName) && pat.getLastName().equals(lastName)) {
                    clinic.getPatients().remove(pat);
                    loggingService.logEvent("removePatient");
                    break;
                }
        }
    }

    public void removeStaff(MedicalClinic clinic, String firstName, String lastName) {
        for (MedicalStaff staff : clinic.getStaff()) {
            if (staff != null && staff.getFirstName() != null && staff.getLastName() != null)
                if (staff.getFirstName().equals(firstName) && staff.getLastName().equals(lastName)){
                    clinic.getStaff().remove(staff);
                    loggingService.logEvent("removeStaff");
                    break;
                }
        }
    }

    public void removeAppointment(MedicalClinic clinic, String date, String docFirstName, String docLastName, String patFirstName, String patLastName) {
        int i = 0;
        for (Appointment a : clinic.getAppointments()) {
            if (a != null && a.getDate() !=null && a.getDoc().getFirstName() != null && a.getDoc().getLastName() != null && a.getPat().getFirstName() != null && a.getPat().getLastName() != null)
                if (a.getDate().equals(date) && a.getDoc().getFirstName().equals(docFirstName) && a.getDoc().getLastName().equals(docLastName) && a.getPat().getFirstName().equals(patFirstName) && a.getPat().getLastName().equals(patLastName)) {
                    clinic.getAppointments().remove(i);
                    loggingService.logEvent("removeAppointment");
                    break;
                }
            i++;
        }
    }

    public void updateAge (Person person, int age) {
        personService.updateAge(person, age);
        if (person instanceof Patient){
            loggingService.logEvent("updatePatient");
        }
        else{
            loggingService.logEvent("updateStaff");
        }
    }

    public void updatePhone (Person person, String phone) {
        personService.updatePhone(person, phone);
        if (person instanceof Patient){
            loggingService.logEvent("updatePatient");
        }
        else{
            loggingService.logEvent("updateStaff");
        }
    }

    public void updateSalary (MedicalStaff staff, float salary) {
        staffService.updateSalary(staff, salary);
        loggingService.logEvent("updateStaff");
    }

    public void updateExperience (MedicalStaff staff, int experience) {
        staffService.updateExperience(staff, experience);
        loggingService.logEvent("updateStaff");
    }

    public void updateDate(Appointment appointment, String date){
        appointmentService.updateDate(appointment, date);
        loggingService.logEvent("updateAppointment");
    }
}
