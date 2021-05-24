package service;

import config.DatabaseConnection;
import model.*;

import javax.print.Doc;
import java.io.*;
import java.nio.file.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public long getNextId(){
        String sql = "select AUTO_INCREMENT from information_schema.TABLES where TABLE_NAME = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, "doctor");
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                return result.getLong(1);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void addDoctor(Doctor doctor) {
        String sql = "insert into doctor values (null, ?, ?, ?, ?, ?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, doctor.getFirstName());
            statement.setString(2, doctor.getLastName());
            statement.setInt(3, doctor.getAge());
            statement.setString(4, doctor.getSex());
            statement.setString(5, doctor.getPhoneNumber());
            statement.setFloat(6, doctor.getSalary());
            statement.setInt(7, doctor.getExperience());
            statement.setString(8, doctor.getBranch());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Doctor> getDoctorById(long id) {
        String sql = "select * from doctor va where va.id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                long doctorId = result.getLong(1);
                String firstName = result.getString(2);
                String lastName = result.getString(3);
                int age = result.getInt(4);
                String sex = result.getString(5);
                String phoneNumber = result.getString(6);
                Float salary = result.getFloat(7);
                int experience = result.getInt(8);
                String branch = result.getString(9);

                return Optional.of(new Doctor(doctorId, firstName, lastName, age, sex, phoneNumber, salary, experience, branch));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void deleteDoctorById(long id){
        String sql = "delete from doctor where id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDoctorById(long id, String field, String value){
        if (field.equals("age")) {
            String sql = "update doctor set age = ? where id = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
                statement.setLong(2, id);
                statement.setInt(1, Integer.parseInt(value));
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (field.equals("phone")) {
            String sql = "update doctor set phoneNumber = ? where id = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
                statement.setLong(2, id);
                statement.setString(1, value);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (field.equals("salary")){
            String sql = "update doctor set salary = ? where id = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
                statement.setLong(2, id);
                statement.setFloat(1, Float.parseFloat(value));
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if (field.equals("experience")){
            String sql = "update doctor set experience = ? where id = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
                statement.setLong(2, id);
                statement.setInt(1, Integer.parseInt(value));
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
