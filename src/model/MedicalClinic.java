package model;

import java.util.ArrayList;
import java.util.List;

public class MedicalClinic {
    private MedicalStaff[] staff = new MedicalStaff[100];
    private Patient[] patients = new Patient[100];
    private List<Appointment> appointments = new ArrayList<Appointment>();

    public MedicalStaff[] getStaff() {
        return staff;
    }

    public void setStaff(MedicalStaff[] staff) {
        this.staff = staff;
    }

    public Patient[] getPatients() {
        return patients;
    }

    public void setPatients(Patient[] patients) {
        this.patients = patients;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
