package model;

public class MedicalClinic {
    private MedicalStaff[] staff;
    private Patient[] patients;

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
}
