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

Lista actiuni:
1 add patient
2 add doctor
3 add assistant
4 add surgery
5 add consultation
6 view patient
7 view staff (doctor + assistant)
8 view appointment (consultation + surgery)
9 get number of medical staff by type
10 get number of appointments by type
11 overview (patients, medical staff, appointments)
12 remove patient
13 remove medical staff
14 remove appointment
15 update person (age, phone)
16 update appointment (date)
17 update staff (salary)

Lista obiecte:
1 MedicalClinic
2 MedicalConsultation
3 MedicalSurgery
4 Patient
5 Doctor
6 Assistant
7 Drug
8 Prescription
9 ClinicalManagement
10 PersonService
11 StaffService
12 AppointmentService

 */


import model.*;
import service.*;

import java.util.*;

public class Application {
    public static void main(String[] args) {
        MedicalClinic clinic = new MedicalClinic();
        ClinicalManagement clinicalManagement = new ClinicalManagement();

        Scanner scanner = new Scanner(System.in);
        //TODO: stergere si actualizare (si o sortare dupa nume pentru pacienti)
        while(true) {
            System.out.println("Please type a command (overview / add / view / stats / remove / update / exit): ");
            String line = scanner.nextLine();
            switch(line) {
                case "overview" :
                    clinicalManagement.showOverview(clinic);
                    break;
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
                                            //TODO: doctor should have assistants (maybe an update that appends them?)
                                            clinicalManagement.addStaff(clinic, doc);
                                            break;
                                        case "assistant" :
                                            System.out.println("Is the assistant a resident? [yes/no]");
                                            String resident = scanner.nextLine();
                                            MedicalStaff assistant;
                                            switch(resident){
                                                case "yes":
                                                    //TODO: check if you can replace this random with something unique (maybe with last array position from clinic)
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

                            System.out.println("Please specify the doctor's first name: ");
                            String docFirstName = scanner.nextLine();
                            System.out.println("Please specify the doctor's last name: ");
                            String docLastName = scanner.nextLine();
                            Doctor doc = clinicalManagement.searchDoctor(clinic, docFirstName, docLastName);
                            if(doc == null){
                                System.out.println("This doctor does not exist !");
                                break;
                            }

                            System.out.println("Please specify the patient's first name: ");
                            String patFirstName = scanner.nextLine();
                            System.out.println("Please specify the patient's last name: ");
                            String patLastName = scanner.nextLine();
                            Patient pat = clinicalManagement.searchPatient(clinic, patFirstName, patLastName);
                            if(pat == null){
                                System.out.println("This patient does not exist !");
                                break;
                            }

                            System.out.println("Please specify the type of appointment (consultation / surgery): ");
                            String appointmentType = scanner.nextLine();
                            switch(appointmentType) {
                                case "consultation":
                                    System.out.println("Please specify the type of disease: ");
                                    String diseaseType = scanner.nextLine();
                                    //TODO maybe create one without prescription and then make an update method ?
                                    MedicalConsultation consultation = new MedicalConsultation(new Random().nextInt(100), date, price, doc, pat, diseaseType);
                                    clinicalManagement.addAppointment(clinic, consultation);
                                    break;
                                case "surgery":
                                    System.out.println("Please specify the type of surgery: ");
                                    String surgeryType = scanner.nextLine();
                                    System.out.println("Please specify the assistant's first name: ");
                                    String asFirstName = scanner.nextLine();
                                    System.out.println("Please specify the assistant's last name: ");
                                    String asLastName = scanner.nextLine();
                                    Assistant as = clinicalManagement.searchAssistant(clinic, asFirstName, asLastName);
                                    if(as == null){
                                        System.out.println("This assistant does not exist !");
                                        break;
                                    }
                                    MedicalSurgery surgery = new MedicalSurgery(new Random().nextInt(100), date, price, doc, pat, surgeryType, as);
                                    clinicalManagement.addAppointment(clinic, surgery);
                                    break;
                                default: System.out.println("This appointment type doesn't exist");
                            }
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
                case "stats" :
                    System.out.println("Please choose what would you like to get statistics for (staff / appointment): ");
                    String statType = scanner.nextLine();
                    switch(statType){
                        case "staff":
                            clinicalManagement.showStatsStaff(clinic);
                            break;
                        case "appointment":
                            clinicalManagement.showStatsAppointments(clinic);
                            break;
                        default:
                            System.out.println("Invalid type");
                    }
                    break;
                case "remove" :
                    System.out.println("Please choose what would you like to remove (staff / patient / appointment): ");
                    String removeType = scanner.nextLine();
                    switch(removeType){
                        case "staff":

                            System.out.println("Please specify staff's first name: ");
                            String staffFirstName = scanner.nextLine();
                            System.out.println("Please specify staff's last name: ");
                            String staffLastName = scanner.nextLine();
                            clinicalManagement.removeStaff(clinic, staffFirstName, staffLastName);
                            break;
                        case "patient":
                            System.out.println("Please specify patient's first name: ");
                            String patFirstName = scanner.nextLine();
                            System.out.println("Please specify patient's last name: ");
                            String patLastName = scanner.nextLine();
                            clinicalManagement.removePatient(clinic, patFirstName, patLastName);
                            break;
                        case "appointment":
                            System.out.println("Please specify appointment's date: ");
                            String aDate = scanner.nextLine();
                            System.out.println("Please specify staff's first name: ");
                            String docFirstName = scanner.nextLine();
                            System.out.println("Please specify staff's last name: ");
                            String docLastName = scanner.nextLine();
                            System.out.println("Please specify patient's first name: ");
                            String pFirstName = scanner.nextLine();
                            System.out.println("Please specify patient's last name: ");
                            String pLastName = scanner.nextLine();
                            clinicalManagement.removeAppointment(clinic, aDate, docFirstName, docLastName, pFirstName, pLastName);
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

