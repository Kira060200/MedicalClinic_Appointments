package model;

public abstract class MedicalStaff {
    protected float salary;
    protected int experience;

    public MedicalStaff(float salary, int experience) {
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
}
