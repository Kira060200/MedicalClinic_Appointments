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

public class RWAssistantService {
    private static final String DIRECTORY_PATH = "resources/data";
    private static final String FILE_PATH = DIRECTORY_PATH + "/assistant.csv";
    private static final RWAssistantService INSTANCE = new RWAssistantService();

    private RWAssistantService() {

    }

    public static RWAssistantService getInstance() {
        return INSTANCE;
    }

    public long getNextId(){
        String sql = "select AUTO_INCREMENT from information_schema.TABLES where TABLE_NAME = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, "assistant");
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                return result.getLong(1);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void addAssistant(Assistant assistant) {
        String sql = "insert into assistant values (null, null, ?, ?, ?, ?, ?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, assistant.getFirstName());
            statement.setString(2, assistant.getLastName());
            statement.setInt(3, assistant.getAge());
            statement.setString(4, assistant.getSex());
            statement.setString(5, assistant.getPhoneNumber());
            statement.setFloat(6, assistant.getSalary());
            statement.setInt(7, assistant.getExperience());
            statement.setInt(8, assistant.isResident() ? 1 : 0);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Assistant> getAssistantById(long id) {
        String sql = "select * from assistant va where va.id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                long assistantId = result.getLong(1);
                String firstName = result.getString(3);
                String lastName = result.getString(4);
                int age = result.getInt(5);
                String sex = result.getString(6);
                String phoneNumber = result.getString(7);
                Float salary = result.getFloat(8);
                int experience = result.getInt(9);
                boolean resident = result.getInt(10) == 1;

                return Optional.of(new Assistant(assistantId, firstName, lastName, age, sex, phoneNumber, salary, experience, resident));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void deleteAssistantById(long id){
        String sql = "delete from assistant where id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAssistantById(long id, String field, String value){
        if (field.equals("age")) {
            String sql = "update assistant set age = ? where id = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
                statement.setLong(2, id);
                statement.setInt(1, Integer.parseInt(value));
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (field.equals("phone")) {
            String sql = "update assistant set phoneNumber = ? where id = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
                statement.setLong(2, id);
                statement.setString(1, value);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (field.equals("salary")){
            String sql = "update assistant set salary = ? where id = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
                statement.setLong(2, id);
                statement.setFloat(1, Float.parseFloat(value));
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if (field.equals("experience")){
            String sql = "update assistant set experience = ? where id = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
                statement.setLong(2, id);
                statement.setInt(1, Integer.parseInt(value));
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if (field.equals("doctor")){
            String sql = "update assistant set idDoctor = ? where id = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
                statement.setLong(2, id);
                statement.setLong(1, Long.parseLong(value));
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
