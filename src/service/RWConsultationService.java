package service;

import config.DatabaseConnection;
import model.*;

import java.io.*;
import java.nio.file.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RWConsultationService {
    private static final String DIRECTORY_PATH = "resources/data";
    private static final String FILE_PATH = DIRECTORY_PATH + "/consultation.csv";
    private static final RWConsultationService INSTANCE = new RWConsultationService();

    private RWConsultationService() {

    }

    public static RWConsultationService getInstance() {
        return INSTANCE;
    }

    public long getNextId(){
        String sql = "select AUTO_INCREMENT from information_schema.TABLES where TABLE_NAME = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, "consultation");
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                return result.getLong(1);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void addConsultation(MedicalConsultation consultation) {
        String sql = "insert into consultation values (null, ?, ?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, consultation.getDate());
            statement.setFloat(2, consultation.getPrice());
            statement.setLong(3, consultation.getDoc().getId());
            statement.setLong(4, consultation.getPat().getId());
            statement.setString(5, consultation.getDisease());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<MedicalConsultation> getConsultationById(long id, MedicalClinic clinic, ClinicalManagement clinicalManagement) {
        String sql = "select * from consultation va where va.id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                String date = result.getString(2);
                float price = result.getFloat(3);
                long docId = result.getLong(4);
                Doctor doctor = clinicalManagement.searchDoctor(clinic, docId);
                if(doctor == null){
                    System.out.println("This doctor does not exist !");
                    return Optional.empty();
                }
                long patId = result.getLong(5);
                Patient patient = clinicalManagement.searchPatient(clinic, patId);
                if(patient == null){
                    System.out.println("This patient does not exist !");
                    return Optional.empty();
                }
                String disease = result.getString(6);

                return Optional.of(new MedicalConsultation(id, date, price, doctor, patient, disease));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void deleteConsultationById(long id){
        String sql = "delete from consultation where id = ?";
        try(PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateConsultationById(long id, String value){
            String sql = "update consultation set date = ? where id = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
                statement.setLong(2, id);
                statement.setString(1, value);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
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
                String date = arr[1];
                float price = Float.parseFloat(arr[2]);
                String docFirstName = arr[3].split("/")[0];
                String docLastName = arr[3].split("/")[1];
                Doctor doctor = clinicalManagement.searchDoctor(clinic, docFirstName, docLastName);
                if(doctor == null){
                    System.out.println("This doctor does not exist !");
                    return;
                }
                String patFirstName = arr[4].split("/")[0];
                String patLastName = arr[4].split("/")[1];
                Patient patient = clinicalManagement.searchPatient(clinic, patFirstName, patLastName);
                if(patient == null){
                    System.out.println("This patient does not exist !");
                    return;
                }
                String disease = arr[5];

                MedicalConsultation appointment = new MedicalConsultation(id, date, price, doctor, patient, disease);
                clinicalManagement.addAppointment(clinic, appointment);
            }
        } catch (NoSuchFileException e) {
            System.out.println("The file with the name " + FILE_PATH + " doesn't exist.");
        } catch (IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
    }

    public void write(List<Appointment> appointments) {
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
            writer.write("id,date,price,doctor,patient,disease\n");
            writer.flush();
            for (Appointment appointment : appointments)
                if (appointment instanceof MedicalConsultation){
                    writer.write(appointment.getId() + "," + appointment.getDate() + "," + appointment.getPrice()
                            + "," + appointment.getDoc().getFirstName() + "/" + appointment.getDoc().getLastName()
                            + "," + appointment.getPat().getFirstName() + "/" + appointment.getPat().getLastName()
                            + "," + ((MedicalConsultation) appointment).getDisease() + "\n");
                    writer.flush();
                }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
