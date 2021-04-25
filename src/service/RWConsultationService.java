package service;

import model.*;

import java.io.*;
import java.nio.file.*;
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
