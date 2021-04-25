package service;

import model.*;

import javax.print.Doc;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RWDoctorService {
    private static final String DIRECTORY_PATH = "resources/data";
    private static final String FILE_PATH = DIRECTORY_PATH + "/doctor.csv";
    private static final RWDoctorService INSTANCE = new RWDoctorService();

    private RWDoctorService() {

    }

    public static RWDoctorService getInstance() {
        return INSTANCE;
    }

    public void read(MedicalClinic clinic, ClinicalManagement clinicalManagement) {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_PATH));
            String line = "";
            line = reader.readLine();
            while((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                long id = Long.parseLong(arr[0]);
                String firstName = arr[1];
                String lastName = arr[2];
                int age = Integer.parseInt(arr[3]);
                String sex = arr[4];
                String phoneNumber = arr[5];
                Float salary = Float.valueOf(arr[6]);
                int experience = Integer.parseInt(arr[7]);
                String branch = arr[8];
                Doctor doctor = new Doctor(id, firstName, lastName, age, sex, phoneNumber, salary, experience, branch);
                clinicalManagement.addStaff(clinic, doctor);
            }
        } catch (NoSuchFileException e) {
            System.out.println("The file with the name " + FILE_PATH + " doesn't exist.");
        } catch (IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
    }

    public void write(Set<MedicalStaff> staff) {
        if(!Files.exists(Paths.get(DIRECTORY_PATH))) {
            try {
                Files.createDirectories(Paths.get(DIRECTORY_PATH));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        if(!Files.exists(Paths.get(FILE_PATH))) {
            try {
                Files.createFile(Paths.get(FILE_PATH));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH),
                    StandardOpenOption.TRUNCATE_EXISTING);
            writer.write("id,firstName,lastName,age,sex,phoneNumber,salary,experience,branch\n");
            writer.flush();
            for (MedicalStaff doctor : staff)
                if (doctor instanceof Doctor){
                    writer.write(doctor.getId() + "," + doctor.getFirstName() + "," + doctor.getLastName() + "," + doctor.getAge() + "," + doctor.getSex() + "," + doctor.getPhoneNumber() + "," + doctor.getSalary() + "," + doctor.getExperience() + "," + ((Doctor) doctor).getBranch() + "\n");
                    writer.flush();
                }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
