package model;

public abstract class MedicalStaff extends Person{
    protected float salary;
    protected int experience;

    public MedicalStaff(long id, String firstName, String lastName, int age, String sex, String phoneNumber, float salary, int experience) {
        super(id, firstName, lastName, age, sex, phoneNumber);
        this.salary = salary;
        this.experience = experience;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return super.toString() + " / " + salary + " / " + experience;
    }
}
