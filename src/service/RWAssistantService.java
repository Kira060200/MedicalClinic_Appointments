package service;

import model.*;
import java.io.*;
import java.nio.file.*;
import java.sql.Timestamp;
import java.util.*;

public class RWAssistantService {
    private static final String DIRECTORY_PATH = "resources/data";
    private static final String FILE_PATH = DIRECTORY_PATH + "/assistant.csv";
    private static final RWAssistantService INSTANCE = new RWAssistantService();

    private RWAssistantService() {

    }

    public static RWAssistantService getInstance() {
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
                boolean resident = Boolean.parseBoolean(arr[8]);
                Assistant assistant = new Assistant(id, firstName, lastName, age, sex, phoneNumber, salary, experience, resident);
                clinicalManagement.addStaff(clinic, assistant);
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
            writer.write("id,firstName,lastName,age,sex,phoneNumber,salary,experience,resident\n");
            writer.flush();
            for (MedicalStaff assistant : staff)
                if (assistant instanceof Assistant){
                    writer.write(assistant.getId() + "," + assistant.getFirstName() + "," + assistant.getLastName() + "," + assistant.getAge() + "," + assistant.getSex() + "," + assistant.getPhoneNumber() + "," + assistant.getSalary() + "," + assistant.getExperience() + "," + ((Assistant) assistant).isResident() + "\n");
                    writer.flush();
                }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
