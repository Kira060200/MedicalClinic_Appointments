package service;

import model.*;

import java.util.Arrays;

public class ClinicalManagement {
    private final PersonService personService = new PersonService();
    private final StaffService staffService = new StaffService();
    private final AppointmentService appointmentService = new AppointmentService();

    public void addStaff(MedicalClinic clinic, MedicalStaff staff) {
        int nextAvailableIndex = getNumberOfStaff(clinic);
        clinic.getStaff()[nextAvailableIndex] = staff;
        Arrays.sort(clinic.getStaff(),0, nextAvailableIndex + 1);
    }

    public void addPatient(MedicalClinic clinic, Patient patient) {
        int nextAvailableIndex = getNumberOfPatients(clinic);
        clinic.getPatients()[nextAvailableIndex] = patient;
        Arrays.sort(clinic.getPatients(),0, nextAvailableIndex + 1);
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

    public Patient searchPatient(MedicalClinic clinic, String firstName, String lastName) {
        for (Patient pat : clinic.getPatients())
            if (pat != null)
                if (pat.getFirstName().equals(firstName) && pat.getLastName().equals(lastName))
                    return pat;
        return null;
    }

    public Doctor searchDoctor(MedicalClinic clinic, String firstName, String lastName) {
        for (MedicalStaff doc : clinic.getStaff())
            if (doc instanceof Doctor)
                if (doc.getFirstName().equals(firstName) && doc.getLastName().equals(lastName))
                    return (Doctor) doc;
        return null;
    }

    public Assistant searchAssistant(MedicalClinic clinic, String firstName, String lastName) {
        for (MedicalStaff as : clinic.getStaff())
            if (as instanceof Assistant)
                if (as.getFirstName().equals(firstName) && as.getLastName().equals(lastName))
                    return (Assistant) as;
        return null;
    }

    public MedicalStaff searchStaff(MedicalClinic clinic, String firstName, String lastName) {
        for (MedicalStaff staff : clinic.getStaff())
                if (staff.getFirstName().equals(firstName) && staff.getLastName().equals(lastName))
                    return staff;
        return null;
    }

    public Appointment searchAppointment(MedicalClinic clinic, String date, String docFirstName, String docLastName, String patFirstName, String patLastName) {
        for (Appointment a : clinic.getAppointments())
            if (a != null)
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

    public void removePatient(MedicalClinic clinic, String firstName, String lastName) {
        int i = 0;
        for (Patient pat : clinic.getPatients()) {
            if (pat != null)
                if (pat.getFirstName().equals(firstName) && pat.getLastName().equals(lastName)) {
                    for (int j = i + 1; j < getNumberOfPatients(clinic); j++)
                        clinic.getPatients()[j - 1] = clinic.getPatients()[j];
                    clinic.getPatients()[getNumberOfPatients(clinic)-1] = null;
                    break;
                }
            i++;
        }
    }

    public void removeStaff(MedicalClinic clinic, String firstName, String lastName) {
        int i = 0;
        for (MedicalStaff staff : clinic.getStaff()) {
            if (staff != null)
                if (staff.getFirstName().equals(firstName) && staff.getLastName().equals(lastName)){
                    for (int j = i + 1; j < getNumberOfStaff(clinic); j++)
                        clinic.getStaff()[j - 1] = clinic.getStaff()[j];
                    clinic.getStaff()[getNumberOfStaff(clinic)-1] = null;
                    break;
                }
            i++;
        }
    }

    public void removeAppointment(MedicalClinic clinic, String date, String docFirstName, String docLastName, String patFirstName, String patLastName) {
        int i = 0;
        for (Appointment a : clinic.getAppointments()) {
            if (a != null)
                if (a.getDate().equals(date) && a.getDoc().getFirstName().equals(docFirstName) && a.getDoc().getLastName().equals(docLastName) && a.getPat().getFirstName().equals(patFirstName) && a.getPat().getLastName().equals(patLastName)) {
                    for (int j = i + 1; j < getNumberOfAppointments(clinic); j++)
                        clinic.getAppointments()[j - 1] = clinic.getAppointments()[j];
                    clinic.getAppointments()[getNumberOfAppointments(clinic)-1] = null;
                    break;
                }
            i++;
        }
    }

    public void updateAge (Person person, int age) {
        personService.updateAge(person, age);
    }

    public void updatePhone (Person person, String phone) {
        personService.updatePhone(person, phone);
    }

    public void updateSalary (MedicalStaff staff, float salary) {
        staffService.updateSalary(staff, salary);
    }

    public void updateExperience (MedicalStaff staff, int experience) {
        staffService.updateExperience(staff, experience);
    }

    public void updateDate(Appointment appointment, String date){
        appointmentService.updateDate(appointment, date);
    }
}
