package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MedicalClinic {
    private Set<MedicalStaff> staff = new TreeSet<>();
    private Set<Patient> patients = new TreeSet<>();
    private List<Appointment> appointments = new ArrayList<>();

    public Set<MedicalStaff> getStaff() {
        return staff;
    }

    public void setStaff(Set<MedicalStaff> staff) {
        this.staff = staff;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
