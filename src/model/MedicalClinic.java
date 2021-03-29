package model;

public class MedicalClinic {
    private MedicalStaff[] staff = new MedicalStaff[100];
    private Patient[] patients = new Patient[100];
    private Appointment[] appointments = new Appointment[100];

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

    public Appointment[] getAppointments() {
        return appointments;
    }

    public void setAppointments(Appointment[] appointments) {
        this.appointments = appointments;
    }
}
