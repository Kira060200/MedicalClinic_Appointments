package service;

import config.DatabaseConnection;
import model.*;
import java.io.*;
import java.nio.file.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public long getNextId(){
        String sql = "select AUTO_INCREMENT from information_schema.TABLES where TABLE_NAME = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, "patient");
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                return result.getLong(1);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void addPatient(Patient patient) {
        String sql = "insert into patient values (null, ?, ?, ?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, patient.getFirstName());
            statement.setString(2, patient.getLastName());
            statement.setInt(3, patient.getAge());
            statement.setString(4, patient.getSex());
            statement.setString(5, patient.getPhoneNumber());
            String disease = "";
            for (String itm : patient.getDisease()){
                disease += (itm + "/");
            }
            boolean res = disease.equals("/");
            disease = disease.replaceFirst(".$","");
            statement.setString(6, ((!res) ? disease : null));
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Patient> getPatientById(long id) {
        String sql = "select * from patient va where va.id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                long patientId = result.getLong(1);
                String firstName = result.getString(2);
                String lastName = result.getString(3);
                int age = result.getInt(4);
                String sex = result.getString(5);
                String phoneNumber = result.getString(6);
                String[] disease = new String[1];
                disease[0]="";
                try {
                    disease = result.getString(7).split("/");
                }catch (Exception ignored){
                }

                return Optional.of(new Patient(patientId, firstName, lastName, age, sex, phoneNumber, disease));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void deletePatientById(long id){
        String sql = "delete from patient where id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePatientById(long id, String field, String value){
        if (field.equals("age")) {
            String sql = "update patient set age = ? where id = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
                statement.setLong(2, id);
                statement.setInt(1, Integer.parseInt(value));
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            String sql = "update patient set phoneNumber = ? where id = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
                statement.setLong(2, id);
                statement.setString(1, value);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
