package model;

public class Doctor extends MedicalStaff{
    private String branch;
    private Assistant[] assistant;

    public Doctor(long id, String firstName, String lastName, int age, String sex, String phoneNumber, float salary, int experience, String branch) {
        super(id, firstName, lastName, age, sex, phoneNumber, salary, experience);
        this.branch = branch;
    }

    public Doctor(long id, String firstName, String lastName, int age, String sex, String phoneNumber, float salary, int experience, String branch, Assistant[] assistant) {
        super(id, firstName, lastName, age, sex, phoneNumber, salary, experience);
        this.branch = branch;
        this.assistant = assistant;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Assistant[] getAssistant() {
        return assistant;
    }

    public void setAssistant(Assistant[] assistant) {
        this.assistant = assistant;
    }
}
