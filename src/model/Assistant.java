package model;

public class Assistant extends MedicalStaff{
    private Doctor doc;

    public Assistant(long id, String firstName, String lastName, int age, String sex, String phoneNumber, float salary, int experience, Doctor doc) {
        super(id, firstName, lastName, age, sex, phoneNumber, salary, experience);
        this.doc = doc;
    }

    public Doctor getDoc() {
        return doc;
    }

    public void setDoc(Doctor doc) {
        this.doc = doc;
    }
}
