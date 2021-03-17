package model;

public class Assistant extends MedicalStaff{
    private boolean resident;

    public Assistant(long id, String firstName, String lastName, int age, String sex, String phoneNumber, float salary, int experience, boolean resident) {
        super(id, firstName, lastName, age, sex, phoneNumber, salary, experience);
        this.resident = resident;
    }

    public boolean isResident() {
        return resident;
    }

    public void setResident(boolean resident) {
        this.resident = resident;
    }

    @Override
    public String toString() { return super.toString() + ((resident) ? " / resident" : ""); }
}
