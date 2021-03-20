package main;

/*

main:
    Application
model:
    1 MedicalClinic ([]MedicalStaff, []Patient, []Appointment)
    2 abstract Appointment (id, date, price, Doctor, Patient)
        2.1 MedicalConsultation (disease, Prescription)
        2.2 MedicalSurgery (type, Assistant)
    3 abstract Person (id, firstName, lastName, age, sex, phoneNumber)
        3.1 Patient ([]disease)
        3.2 abstract MedicalStaff (salary, experience)
            3.2.1 Doctor (branch, []Assistant)
            3.2.2 Assistant (boolean resident)
    4 Drug (id, name, price)
    5 Prescription (id, []Drug)
service:
    ClinicalManagement

List actiuni:
1 add patient
2 add doctor
3 add assistant
4 add surgery
5 add consultation
6 view patient
7 view doctor
8 view assistant
9 view surgery
10 view consultation
11 get number of patients with diseases
12 get number of medical staff by type
13 get number of appointments by type
14 overview (patients, medical staff, appointments)

List obiecte:
1 MedicalClinic
2 MedicalConsultation
3 MedicalSurgery
4 Patient
5 Doctor
6 Assistant
7 Drug
8 Prescription
9 ClinicalManagement

 */


import model.*;
import service.*;

import java.util.*;

public class Application {
    public static void main(String[] args) {
        MedicalClinic clinic = new MedicalClinic();
        ClinicalManagement clinicalManagement = new ClinicalManagement();

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Please type a command: ");
            String line = scanner.nextLine();
            switch(line) {
                case "add":
                    System.out.println("Please choose what would you like to add (person / appointment): ");
                    String pick = scanner.nextLine();
                    switch(pick){
                        case "person":
                            System.out.println("Please specify first name: ");
                            String firstName = scanner.nextLine();
                            System.out.println("Please specify last name: ");
                            String lastName = scanner.nextLine();
                            System.out.println("Please specify age: ");
                            int age = Integer.valueOf(scanner.nextLine());
                            System.out.println("Please specify sex: ");
                            String sex = scanner.nextLine();
                            System.out.println("Please specify phone number: ");
                            String phoneNumber = scanner.nextLine();
                            System.out.println("Please choose person type (staff / patient): ");
                            String line2 = scanner.nextLine();
                            switch(line2){
                                case "staff" :
                                    System.out.println("Please specify the salary: ");
                                    float salary = Float.valueOf(scanner.nextLine());
                                    System.out.println("Please specify experience in years: ");
                                    int experience = Integer.valueOf(scanner.nextLine());
                                    System.out.println("Please choose medical staff (doctor / assistant): ");
                                    String staffType = scanner.nextLine();
                                    switch(staffType) {
                                        case "doctor" :
                                            System.out.println("Please specify branch: ");
                                            String branch = scanner.nextLine();
                                            //Assistant[] assistant = new Assistant[10];
                                            MedicalStaff doc = new Doctor(new Random().nextInt(100), firstName, lastName, age, sex, phoneNumber, salary, experience, branch);
                                            clinicalManagement.addStaff(clinic, doc);
                                            break;
                                        case "assistant" :
                                            System.out.println("Is the assistant a resident? [yes/no]");
                                            String resident = scanner.nextLine();
                                            MedicalStaff assistant;
                                            switch(resident){
                                                case "yes":
                                                    assistant = new Assistant(new Random().nextInt(100), firstName, lastName, age, sex, phoneNumber, salary, experience, true);
                                                    clinicalManagement.addStaff(clinic, assistant);
                                                    break;
                                                case "no":
                                                    assistant = new Assistant(new Random().nextInt(100), firstName, lastName, age, sex, phoneNumber, salary, experience, false);
                                                    clinicalManagement.addStaff(clinic, assistant);
                                                    break;
                                                default: System.out.println("Invalid answer.");
                                            }
                                            break;
                                        default : System.out.println("This staff type doesn't exist.");
                                    }
                                    break;
                                case "patient" :
                                    System.out.println("Please specify if the patient has any know diseases: ");
                                    String diseaseDetails = scanner.nextLine();
                                    String[] disease = diseaseDetails.split("/");
                                    Patient person = new Patient(new Random().nextInt(100), firstName, lastName, age, sex, phoneNumber, disease);
                                    clinicalManagement.addPatient(clinic, person);
                                    break;
                                default : System.out.println("This person type doesn't exist.");
                            }
                            break;
                        case "appointment":
                            System.out.println("Please specify the date for the appointment: ");
                            String date = scanner.nextLine();
                            System.out.println("Please specify the price: ");
                            float price = Float.valueOf(scanner.nextLine());
                            System.out.println("Please specify the doctor: ");
                            //TODO add class method to get the doctor by name
                            String docName = scanner.nextLine();
                            //doc = ...
                            //TODO add class method to get the patient by name
                            System.out.println("Please specify the patient: ");
                            //pat = ...
                            System.out.println("Please specify the type of appointment (consultation / surgery): ");
                            String appointmentType = scanner.nextLine();
                            switch(appointmentType) {
                                case "consultation: ":
                                    //TODO
                                    break;
                                case "surgery: ":
                                    //TODO
                                    break;
                                default: System.out.println("This appointment type doesn't exist");
                            }
                            //TODO create the appointment and add it to MedicalClinic
                            break;
                        default: System.out.println("Invalid type");
                    }
                    break;
                case "view" :
                    System.out.println("Please choose what would you like to visualize (staff / patient / appointment): ");
                    String type = scanner.nextLine();
                    switch(type){
                        case "staff":
                            clinicalManagement.printStaffDetails(clinic);
                            break;
                        case "patient":
                            clinicalManagement.printPatientsDetails(clinic);
                            break;
                        case "appointment":
                            clinicalManagement.printAppointmentsDetails(clinic);
                            break;
                        default:
                            System.out.println("Invalid type");
                    }
                    break;
                case "exit" :
                    System.out.println("Bye bye!");
                    System.exit(0);
                    break;
                default : System.out.println("This command doesn't exist.");
            }

        }

    }
}

