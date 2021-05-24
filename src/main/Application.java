package main;

/*

main:
    Application
model:
    1 MedicalClinic (TreeSet MedicalStaff, TreeSet Patient, ArrayList Appointment)
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
    PersonService
    StaffService
    AppointmentService

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
15 update patient (age, phone)
16 update staff (age, phone, salary, experience)
17 update appointment (date)
18 get patient (from db)
19 get doctor (from db)
20 get assistant (from db)
21 get consultation (from db)
22 get surgery (from db)

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
13 LoggingService
 */


import model.*;
import service.*;

import java.util.*;

public class Application {
    public static void main(String[] args) {
        MedicalClinic clinic = new MedicalClinic();
        ClinicalManagement clinicalManagement = new ClinicalManagement();
        RWPatientService rwPatientService = RWPatientService.getInstance();
        rwPatientService.read(clinic, clinicalManagement);
        RWAssistantService rwAssistantService = RWAssistantService.getInstance();
        rwAssistantService.read(clinic, clinicalManagement);
        RWDoctorService rwDoctorService = RWDoctorService.getInstance();
        rwDoctorService.read(clinic, clinicalManagement);
        RWConsultationService rwConsultationService = RWConsultationService.getInstance();
        rwConsultationService.read(clinic, clinicalManagement);
        RWSurgeryService rwSurgeryService = RWSurgeryService.getInstance();
        rwSurgeryService.read(clinic, clinicalManagement);
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Please type a command (overview / add / get / view / stats / remove / update / exit): ");
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
                            newPerson(scanner, clinicalManagement, clinic);
                            break;
                        case "appointment":
                            newAppointment(scanner, clinicalManagement, clinic);
                            break;
                        default: System.out.println("Invalid type");
                    }
                    break;
                case "get":
                    System.out.println("Please choose what would you like to get from db (patient / doctor / assistant / consultation / surgery): ");
                    String getPick = scanner.nextLine();
                    System.out.println("Please enter the id: ");
                    long pickedId = Long.parseLong(scanner.nextLine());
                    switch(getPick){
                        case "patient":
                            Optional<Patient> patient = rwPatientService.getPatientById(pickedId);
                            if(patient.isPresent()) {
                                System.out.println(patient.get());
                            }
                            break;
                        case "doctor":
                            Optional<Doctor> doctor = rwDoctorService.getDoctorById(pickedId);
                            if(doctor.isPresent()) {
                                System.out.println(doctor.get());
                            }
                            break;
                        case "assistant":
                            Optional<Assistant> assistant = rwAssistantService.getAssistantById(pickedId);
                            if(assistant.isPresent()) {
                                System.out.println(assistant.get());
                            }
                            break;
                        case "consultation":
                            Optional<MedicalConsultation> consultation = rwConsultationService.getConsultationById(pickedId, clinic, clinicalManagement);
                            if(consultation.isPresent()) {
                                System.out.println(consultation.get());
                            }
                            break;
                        case "surgery":
                            Optional<MedicalSurgery> surgery = rwSurgeryService.getSurgeryById(pickedId, clinic, clinicalManagement);
                            if(surgery.isPresent()) {
                                System.out.println(surgery.get());
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
                            removeStaff(scanner, clinicalManagement, clinic);
                            break;
                        case "patient":
                            removePatient(scanner, clinicalManagement, clinic);
                            break;
                        case "appointment":
                            removeAppointment(scanner, clinicalManagement, clinic);
                            break;
                        default:
                            System.out.println("Invalid type");
                    }
                    break;
                case "update" :
                    System.out.println("Please choose what would you like to update (patient / staff / appointment): ");
                    String updateType = scanner.nextLine();
                    switch(updateType){
                        case "patient":
                            updatePatient(scanner, clinicalManagement, clinic);
                            break;
                        case "staff":
                            updateStaff(scanner, clinicalManagement, clinic);
                            break;
                        case "appointment":
                            updateAppointment(scanner, clinicalManagement, clinic);
                            break;
                        default:
                            System.out.println("Invalid type");
                    }
                    break;
                case "exit" :
                    rwPatientService.write(clinic.getPatients());
                    rwAssistantService.write(clinic.getStaff());
                    rwDoctorService.write(clinic.getStaff());
                    rwConsultationService.write(clinic.getAppointments());
                    rwSurgeryService.write(clinic.getAppointments());
                    System.out.println("Bye bye!");
                    System.exit(0);
                    break;
                default : System.out.println("This command doesn't exist.");
            }

        }

    }

    private static void newPerson(Scanner scanner, ClinicalManagement clinicalManagement, MedicalClinic clinic) {
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
                newStaff(scanner, firstName, lastName, age, sex, phoneNumber, clinicalManagement, clinic);
                break;
            case "patient" :
                newPatient(scanner, firstName, lastName, age, sex, phoneNumber, clinicalManagement, clinic);
                break;
            default : System.out.println("This person type doesn't exist.");
        }
    }

    private static void newPatient(Scanner scanner, String firstName, String lastName, int age, String sex, String phoneNumber, ClinicalManagement clinicalManagement, MedicalClinic clinic) {
        System.out.println("Please specify if the patient has any know diseases: ");
        String diseaseDetails = scanner.nextLine();
        String[] disease = diseaseDetails.split("/");
        RWPatientService rwPatientService = RWPatientService.getInstance();
        long id = rwPatientService.getNextId();
        Patient person = new Patient(id, firstName, lastName, age, sex, phoneNumber, disease);
        clinicalManagement.addPatient(clinic, person);
        rwPatientService.addPatient(person);
    }

    private static void newStaff(Scanner scanner, String firstName, String lastName, int age, String sex, String phoneNumber, ClinicalManagement clinicalManagement, MedicalClinic clinic){
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
                System.out.println("Does this doctor have an assistant? [yes/no] (yes = it has to be already added in the clinic database)");
                String checkAssistant = scanner.nextLine();
                RWDoctorService rwDoctorService = RWDoctorService.getInstance();
                long id = rwDoctorService.getNextId();
                switch(checkAssistant){
                    case "yes":
                        int number = 0;
                        System.out.println("How many? ");
                        number = Integer.valueOf(scanner.nextLine());
                        Assistant[] assistantArray = new Assistant[number];
                        int i = 0;
                        Long[] asIdArr = new Long[number];
                        while(i < number){
                            System.out.println("Please specify the assistant's id: ");
                            long asId = Long.parseLong(scanner.nextLine());
                            Assistant as = clinicalManagement.searchAssistant(clinic, asId);
                            if(as == null){
                                System.out.println("This assistant does not exist !");
                                break;
                            }
                            else{
                                assistantArray[i] = as;
                                asIdArr[i] = asId;
                                i++;

                            }
                        }
                        MedicalStaff doctor = new Doctor(id, firstName, lastName, age, sex, phoneNumber, salary, experience, branch, assistantArray.clone());
                        clinicalManagement.addStaff(clinic, doctor);
                        rwDoctorService.addDoctor((Doctor) doctor);
                        RWAssistantService rwAssistantService = RWAssistantService.getInstance();
                        for(int j=0; j < asIdArr.length;j++)
                            rwAssistantService.updateAssistantById(asIdArr[j], "doctor", String.valueOf(id));
                        break;
                    case "no":
                        MedicalStaff doc = new Doctor(id, firstName, lastName, age, sex, phoneNumber, salary, experience, branch);
                        clinicalManagement.addStaff(clinic, doc);
                        rwDoctorService.addDoctor((Doctor) doc);
                        break;
                    default: System.out.println("Invalid answer.");
                }

                break;
            case "assistant" :
                System.out.println("Is the assistant a resident? [yes/no]");
                String resident = scanner.nextLine();
                MedicalStaff assistant;
                RWAssistantService rwAssistantService = RWAssistantService.getInstance();
                long asId = rwAssistantService.getNextId();
                switch(resident){
                    case "yes":
                        assistant = new Assistant(asId, firstName, lastName, age, sex, phoneNumber, salary, experience, true);
                        clinicalManagement.addStaff(clinic, assistant);
                        rwAssistantService.addAssistant((Assistant) assistant);
                        break;
                    case "no":
                        assistant = new Assistant(asId, firstName, lastName, age, sex, phoneNumber, salary, experience, false);
                        clinicalManagement.addStaff(clinic, assistant);
                        rwAssistantService.addAssistant((Assistant) assistant);
                        break;
                    default: System.out.println("Invalid answer.");
                }
                break;
            default : System.out.println("This staff type doesn't exist.");
        }
    }

    private static void newAppointment(Scanner scanner, ClinicalManagement clinicalManagement, MedicalClinic clinic){
        System.out.println("Please specify the date for the appointment: ");
        String date = scanner.nextLine();
        System.out.println("Please specify the price: ");
        float price = Float.valueOf(scanner.nextLine());

        System.out.println("Please specify the doctor's id: ");
        long docId = Long.parseLong(scanner.nextLine());
        Doctor doc = clinicalManagement.searchDoctor(clinic, docId);
        if(doc == null){
            System.out.println("This doctor does not exist !");
            return;
        }

        System.out.println("Please specify the patient's id: ");
        long patId = Long.parseLong(scanner.nextLine());
        Patient pat = clinicalManagement.searchPatient(clinic, patId);
        if(pat == null){
            System.out.println("This patient does not exist !");
            return;
        }

        System.out.println("Please specify the type of appointment (consultation / surgery): ");
        String appointmentType = scanner.nextLine();
        switch(appointmentType) {
            case "consultation":
                System.out.println("Please specify the type of disease: ");
                String diseaseType = scanner.nextLine();
                System.out.println("Does this consultation have an prescription? [yes/no]");
                String checkPrescription = scanner.nextLine();
                RWConsultationService rwConsultationService = RWConsultationService.getInstance();
                long consultationId = rwConsultationService.getNextId();
                switch(checkPrescription){
                    case "yes":
                        int number = 0;
                        System.out.println("How many drugs? ");
                        number = Integer.valueOf(scanner.nextLine());
                        Drug[] drugArray = new Drug[number];
                        int i = 0;
                        while(i < number){
                            System.out.println("Please specify drug's name: ");
                            String drugName = scanner.nextLine();
                            System.out.println("Please specify drug's price: ");
                            float drugPrice = Float.valueOf(scanner.nextLine());
                            drugArray[i] = new Drug(new Random().nextInt(100), drugName, drugPrice);
                            i++;
                        }
                        MedicalConsultation consultation = new MedicalConsultation(consultationId, date, price, doc, pat, diseaseType, new Prescription(new Random().nextInt(100), drugArray.clone()));
                        clinicalManagement.addAppointment(clinic, consultation);
                        rwConsultationService.addConsultation(consultation);
                        break;
                    case "no":
                        MedicalConsultation cons = new MedicalConsultation(consultationId, date, price, doc, pat, diseaseType);
                        clinicalManagement.addAppointment(clinic, cons);
                        rwConsultationService.addConsultation(cons);
                        break;
                    default: System.out.println("Invalid answer.");
                }
                break;
            case "surgery":
                RWSurgeryService rwSurgeryService = RWSurgeryService.getInstance();
                long surgeryId = rwSurgeryService.getNextId();
                System.out.println("Please specify the type of surgery: ");
                String surgeryType = scanner.nextLine();
                System.out.println("Please specify the assistant's id: ");
                long asId = Long.parseLong(scanner.nextLine());
                Assistant as = clinicalManagement.searchAssistant(clinic, asId);
                if(as == null){
                    System.out.println("This assistant does not exist !");
                    break;
                }
                MedicalSurgery surgery = new MedicalSurgery(surgeryId, date, price, doc, pat, surgeryType, as);
                clinicalManagement.addAppointment(clinic, surgery);
                rwSurgeryService.addSurgery(surgery);
                break;
            default: System.out.println("This appointment type doesn't exist");
        }
    }

    private static void updatePatient(Scanner scanner, ClinicalManagement clinicalManagement, MedicalClinic clinic){
        System.out.println("Please specify the patient's id: ");
        int patientId = Integer.parseInt(scanner.nextLine());
        RWPatientService rwPatientService = RWPatientService.getInstance();
        Patient pat = clinicalManagement.searchPatient(clinic, patientId);
        if(pat == null){
            System.out.println("This patient does not exist !");
            return;
        }
        System.out.println("Please specify what would you like to update (age / phone): ");
        String updateAnswer = scanner.nextLine();
        switch (updateAnswer){
            case "age":
                System.out.println("Please specify age: ");
                int age = Integer.valueOf(scanner.nextLine());
                clinicalManagement.updateAge(pat, age);
                rwPatientService.updatePatientById(patientId, "age", String.valueOf(age));
                break;
            case "phone":
                System.out.println("Please specify the phone number: ");
                String phone = scanner.nextLine();
                clinicalManagement.updatePhone(pat, phone);
                rwPatientService.updatePatientById(patientId, "phone", phone);
                break;
            default:System.out.println("Invalid type");
        }
    }

    private static void updateStaff(Scanner scanner, ClinicalManagement clinicalManagement, MedicalClinic clinic){
        System.out.println("Please specify staff member's role: doctor / assistant");
        String staffRole = scanner.nextLine();
        System.out.println("Please specify staff member's id: ");
        long id = Long.parseLong(scanner.nextLine());
        MedicalStaff staff;
        RWDoctorService rwDoctorService = RWDoctorService.getInstance();
        RWAssistantService rwAssistantService = RWAssistantService.getInstance();
        if (staffRole.equals("doctor")){
            staff = clinicalManagement.searchDoctor(clinic, id);
        }
        else if (staffRole.equals("assistant")) {
            staff = clinicalManagement.searchAssistant(clinic, id);
        }
        else{
            System.out.println("Invalid type");
            return;
        }
        if(staff == null){
            System.out.println("This staff member does not exist !");
            return;
        }
        System.out.println("Please specify what would you like to update (age / phone / salary / experience): ");
        String updateStaffAnswer = scanner.nextLine();
        switch (updateStaffAnswer){
            case "age":
                System.out.println("Please specify age: ");
                int age = Integer.valueOf(scanner.nextLine());
                clinicalManagement.updateAge(staff, age);
                if (staffRole.equals("doctor")){
                    rwDoctorService.updateDoctorById(id, "age", String.valueOf(age));
                }
                else if (staffRole.equals("assistant")) {
                    rwAssistantService.updateAssistantById(id, "age", String.valueOf(age));
                }
                break;
            case "phone":
                System.out.println("Please specify the phone number: ");
                String phone = scanner.nextLine();
                clinicalManagement.updatePhone(staff, phone);
                if (staffRole.equals("doctor")){
                    rwDoctorService.updateDoctorById(id, "phone", phone);
                }
                else if (staffRole.equals("assistant")) {
                    rwAssistantService.updateAssistantById(id, "phone", phone);
                }
                break;
            case "salary":
                System.out.println("Please specify salary: ");
                float salary = Float.valueOf(scanner.nextLine());
                clinicalManagement.updateSalary(staff, salary);
                if (staffRole.equals("doctor")){
                    rwDoctorService.updateDoctorById(id, "salary", String.valueOf(salary));
                }
                else if (staffRole.equals("assistant")) {
                    rwAssistantService.updateAssistantById(id, "salary", String.valueOf(salary));
                }
                break;
            case "experience":
                System.out.println("Please specify experience: ");
                int experience = Integer.valueOf(scanner.nextLine());
                clinicalManagement.updateExperience(staff, experience);
                if (staffRole.equals("doctor")){
                    rwDoctorService.updateDoctorById(id, "experience", String.valueOf(experience));
                }
                else if (staffRole.equals("assistant")) {
                    rwAssistantService.updateAssistantById(id, "experience", String.valueOf(experience));
                }
                break;
            default:System.out.println("Invalid type");
        }
    }

    private static void updateAppointment(Scanner scanner, ClinicalManagement clinicalManagement, MedicalClinic clinic){
        System.out.println("Please specify appointment's type: consultation / surgery");
        String appType = scanner.nextLine();
        System.out.println("Please specify appointment's id: ");
        long id = Long.parseLong(scanner.nextLine());
        Appointment appointment;
        RWConsultationService rwConsultationService = RWConsultationService.getInstance();
        RWSurgeryService rwSurgeryService = RWSurgeryService.getInstance();
        if (appType.equals("consultation")){
            appointment = clinicalManagement.searchConsultation(clinic, id);
        }
        else if (appType.equals("surgery")) {
            appointment = clinicalManagement.searchSurgery(clinic, id);
        }
        else{
            System.out.println("Invalid type");
            return;
        }

        if(appointment == null){
            System.out.println("This appointment does not exist !");
            return;
        }
        System.out.println("Please specify the new date for the appointment: ");
        String newDate = scanner.nextLine();
        clinicalManagement.updateDate(appointment, newDate);
        if (appType.equals("consultation")){
            rwConsultationService.updateConsultationById(id, newDate);
        }
        else if (appType.equals("surgery")) {
            rwSurgeryService.updateSurgeryById(id, newDate);
        }
    }

    private static void removeStaff(Scanner scanner, ClinicalManagement clinicalManagement, MedicalClinic clinic){
        System.out.println("Please specify staff member's role: doctor / assistant");
        String staffRole = scanner.nextLine();
        System.out.println("Please specify staff member's id: ");
        long id = Long.parseLong(scanner.nextLine());
        if (staffRole.equals("doctor")){
            RWDoctorService rwDoctorService = RWDoctorService.getInstance();
            clinicalManagement.removeDoctor(clinic, id);
            rwDoctorService.deleteDoctorById(id);
        }
        else if (staffRole.equals("assistant")) {
            RWAssistantService rwAssistantService = RWAssistantService.getInstance();
            clinicalManagement.removeAssistant(clinic, id);
            rwAssistantService.deleteAssistantById(id);
        }


    }

    private static void removePatient(Scanner scanner, ClinicalManagement clinicalManagement, MedicalClinic clinic){
        System.out.println("Please specify patient's id: ");
        long patientId = Long.parseLong(scanner.nextLine());
        RWPatientService rwPatientService = RWPatientService.getInstance();
        rwPatientService.deletePatientById(patientId);
        clinicalManagement.removePatient(clinic, patientId);
    }

    private static void removeAppointment(Scanner scanner, ClinicalManagement clinicalManagement, MedicalClinic clinic){
        System.out.println("Please specify appointment's type: consultation / surgery");
        String appType = scanner.nextLine();
        System.out.println("Please specify appointment's id: ");
        long id = Long.parseLong(scanner.nextLine());
        if (appType.equals("consultation")){
            RWConsultationService rwConsultationService = RWConsultationService.getInstance();
            clinicalManagement.removeConsultation(clinic, id);
            rwConsultationService.deleteConsultationById(id);
        }
        else if (appType.equals("surgery")) {
            RWSurgeryService rwSurgeryService = RWSurgeryService.getInstance();
            clinicalManagement.removeSurgery(clinic, id);
            rwSurgeryService.deleteSurgeryById(id);
        }
    }
}

