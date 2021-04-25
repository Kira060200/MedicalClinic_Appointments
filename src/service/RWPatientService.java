package service;

import model.*;
import java.io.*;
import java.nio.file.*;
import java.sql.Timestamp;
import java.util.*;

public class RWPatientService {
    private static final String DIRECTORY_PATH = "resources/data";
    private static final String FILE_PATH = DIRECTORY_PATH + "/patient.csv";
    private static final RWPatientService INSTANCE = new RWPatientService();

    private RWPatientService() {

    }

    public static RWPatientService getInstance() {
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
                String[] disease = new String[1];
                disease[0]="";
                try {
                    disease = arr[6].split("/");
                }catch (Exception ignored){
                }
                Patient person = new Patient(id, firstName, lastName, age, sex, phoneNumber, disease);
                clinicalManagement.addPatient(clinic, person);
            }
        } catch (NoSuchFileException e) {
            System.out.println("The file with the name " + FILE_PATH + " doesn't exist.");
        } catch (IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
    }

    public void write(Set<Patient> patients) {
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
            writer.write("id,firstName,lastName,age,sex,phoneNumber,disease\n");
            writer.flush();
            for(Patient patient : patients) {
                if(patient != null) {
                    String d = "";
                    for (String itm : patient.getDisease()){
                        d += (itm + "/");
                    }
                    boolean res = d.equals("/");
                    d = d.replaceFirst(".$","");
                    writer.write(patient.getId() + "," + patient.getFirstName() + "," + patient.getLastName() + "," + patient.getAge() + "," + patient.getSex() + "," + patient.getPhoneNumber() + ((!res) ? ("," + d) : "") + "\n");
                    writer.flush();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
