package service;

import model.MedicalStaff;

public class StaffService {
    public void updateSalary (MedicalStaff staff, float salary) {
        staff.setSalary(salary);
    }
    public void updateExperience (MedicalStaff staff, int experience) {
        staff.setExperience(experience);
    }
}
