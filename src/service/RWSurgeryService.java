package service;

import model.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RWSurgeryService {
    private static final String DIRECTORY_PATH = "resources/data";
    private static final String FILE_PATH = DIRECTORY_PATH + "/surgery.csv";
    private static final RWSurgeryService INSTANCE = new RWSurgeryService();

    private RWSurgeryService() {

    }

    public static RWSurgeryService getInstance() {
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
                String type = arr[5];
                String asFirstName = arr[6].split("/")[0];
                String asLastName = arr[6].split("/")[1];
                Assistant assistant = clinicalManagement.searchAssistant(clinic, asFirstName, asLastName);
                if(assistant == null){
                    System.out.println("This assistant does not exist !");
                    return;
                }

                MedicalSurgery appointment = new MedicalSurgery(id, date, price, doctor, patient, type, assistant);
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
            writer.write("id,date,price,doctor,patient,type,assistant\n");
            writer.flush();
            for (Appointment appointment : appointments)
                if (appointment instanceof MedicalSurgery){
                    writer.write(appointment.getId() + "," + appointment.getDate() + "," + appointment.getPrice()
                            + "," + appointment.getDoc().getFirstName() + "/" + appointment.getDoc().getLastName()
                            + "," + appointment.getPat().getFirstName() + "/" + appointment.getPat().getLastName()
                            + "," + ((MedicalSurgery) appointment).getType()
                            + "," + ((MedicalSurgery) appointment).getAs().getFirstName() + "/" + ((MedicalSurgery) appointment).getAs().getLastName() + "\n");
                    writer.flush();
                }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
