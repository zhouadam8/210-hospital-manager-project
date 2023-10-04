package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

import model.*;
import javax.swing.*;

// EFFECTS: Hospital Application
public class HospitalApp {
    private ArrayList<Doctor> doctors;
    private ArrayList<Patient> patients;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/hospital.json";
    private int id = 1;

    // EFFECTS: Hospital Application
    public HospitalApp() {
        runHospital();
    }

    // MODIFIES: this
    // EFFECTS: processes input
    private void runHospital() {
        boolean keepGoing = true;
        String command = null;

        init();

        System.out.println("Welcome to the Hospital");

        while (keepGoing) {
            displayMenu();
            command = input.next();

            if (command.equals("5")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("Goodbye!");

    }

    // MODIFIES: this
    // EFFECTS: sets up list of doctors and patients
    private void init() {
        this.doctors = new ArrayList<>();
        this.patients = new ArrayList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays options in console
    private void displayMenu() {
        System.out.println(" ");
        System.out.println("\nWould you like to");
        System.out.println("\t1 -> Add New Doctor");
        System.out.println("\t2 -> View All Doctors");
        System.out.println("\t3 -> Select Existing Patient");
        System.out.println("\t4 -> Add New Patient");
        System.out.println("\ts -> save work room to file");
        System.out.println("\tl -> load work room from file");
        System.out.println("\t5 -> Exit");
    }

    // MODIFIES: this
    // EFFECTS: processes different inputs
    private void processCommand(String command) {
        if (command.equals("1")) {
            doAddDoctor();
        } else if (command.equals("2")) {
            doShowDoctor();
        } else if (command.equals("3")) {
            doSelectPatient();
        } else if (command.equals("4")) {
            doNewPatient();
        } else if (command.equals("s")) {
            saveHospitalStatus();
        } else if (command.equals("l")) {
            loadHospitalStatus();
        } else {
            System.out.println("Selection not valid");
        }
    }

    // EFFECTS: saves the current status of the hospital as a JSON file
    private void saveHospitalStatus() {
        try {
            jsonWriter.open();
            jsonWriter.write(doctors, patients, id);
            jsonWriter.close();
            System.out.println("Saved Hospital to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to fileL " + JSON_STORE);
        }
    }

    // EFFECTS: loads the previous saved JSON file
    private void loadHospitalStatus() {
        try {
            ArrayList<Object> result = jsonReader.read();
            this.doctors = (ArrayList<Doctor>) result.get(0);
            this.patients = (ArrayList<Patient>) result.get(1);
            this.id = (int) result.get(2);
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // EFFECTS: displays all available doctors or tells the user there is no existing doctors
    private void doShowDoctor() {
        int index = 0;
        if (doctors.isEmpty()) {
            System.out.println("No Pre-existing Doctors to choose from.");
        } else {
            for (Doctor d : doctors) {
                index = index + 1;
                System.out.println(index + " - " + d.getName());
            }
            System.out.println("\nPlease type the input number of the doctor to see their patients");
            showPatients(input.nextInt());
        }
    }

    // EFFECTS: shows all added patients or tells the user there is none
    private void showPatients(int index) {
        Doctor doctor;
        List<Patient> docPatients;
        index = index - 1;
        if (index <= doctors.size() - 1) {
            doctor = doctors.get(index);
            docPatients = doctor.getPatients();

            for (Patient p : docPatients) {
                String name = p.getName();
                System.out.println("- " + name);
            }
            System.out.println("- End of Patients -");
        } else {
            System.out.println("No Patient with this number");
        }
    }

    // MODIFIES: this
    // EFFECTS: determines which patient to be selected
    private void doSelectPatient() {
        int index = 0;
        if (patients.isEmpty()) {
            System.out.println("No Pre-existing Patients to choose from.");
        } else {
            for (Patient p : patients) {
                index = index + 1;
                boolean condition = p.getHospitalized();
                System.out.println(index + " - " + p.getName() + p.condText());
            }
            System.out.println("\nPlease type the input number of the patient to be selected");
            followingTask(input.nextInt());
        }
    }


    // MODIFIES: this
    // EFFECTS: determines the task to do on the patient selected
    private void followingTask(int index) {
        Patient patient;
        String command = null;
        index = index - 1;
        patient = patients.get(index);
        followingPrint(patient);

        command = input.next();
        if (command.equals("1")) {
            doAdmit(patient);
        } else if (command.equals("2")) {
            doDischarge(patient);
        } else if (command.equals("3")) {
            doSeeHistory(patient);
        } else if (command.equals("4")) {
            doChangeHeight(patient);
        } else if (command.equals("5")) {
            doChangeWeight(patient);
        } else if (command.equals("6")) {
            System.out.println("Returning to Menu...");
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the height of the selected patient
    private void doChangeHeight(Patient patient) {
        int height;
        System.out.println("Please enter the new height(cm)");
        height = input.nextInt();
        patient.setHeight(height);
        System.out.println("Height has been changed to " + height + "cm");
    }

    // MODIFIES: this
    // EFFECTS: changes the weight of the selected patient
    private void doChangeWeight(Patient patient) {
        int weight;
        System.out.println("Please enter the new weight(kg)");
        weight = input.nextInt();
        patient.setWeight(weight);
        System.out.println("Weight has been changed to " + weight + "kg");

    }

    // MODIFIES: this
    // EFFECTS: prints the patient information and the options available
    private void followingPrint(Patient patient) {
        System.out.println("Patient Name: " + patient.getName());
        System.out.println("\tWeight: " + patient.getWeight());
        System.out.println("\tHeight: " + patient.getHeight());
        System.out.println("\nWould you like to");
        System.out.println("\t1 -> Admit Patient");
        System.out.println("\t2 -> Discharge Patient");
        System.out.println("\t3 -> See Medical History");
        System.out.println("\t4 -> Change Height");
        System.out.println("\t5 -> Change Weight");
        System.out.println("\t6 -> Cancel");
    }

    // MODIFIES: this
    // EFFECTS: discharges the patient
    private void doDischarge(Patient patient) {
        String command;
        String date;
        Doctor doctor;
        if (patient.getHospitalized()) {
            System.out.println("Are you sure you want to discharge the patient (type yes to discharge)");
            command = input.next();
            if (command.equals("yes")) {
                System.out.println("Please enter the date (dd/mm/year)");
                date = input.next();
                doctor = searchDoctors(patient.getDoctor());
                doctor.removePatient(patient);
                patient.cured(date);
                System.out.println("Patient Discharged");
            }
            System.out.println("\n Returning to Menu...");
        } else {
            System.out.println("Patient is Not Hospitalized");
        }
    }

    // MODIFIES: this
    // EFFECTS: prints the medical history of the patient
    private void doSeeHistory(Patient patient) {
        List<MedStatus> history;
        history = patient.getHistory();
        for (MedStatus m : history) {
            String initialDate = m.getInitialDate();
            String finalDate = m.getFinalDate();
            String reason = m.getReason();
            String doctor = m.getDoctor();
            System.out.println("\n" + initialDate + " - " + finalDate);
            System.out.println("\t Reason: " + reason);
            System.out.println("\t- Doctor: " + doctor);
        }
        System.out.println("- End of Medical History -");
    }

    // MODIFIES: this
    // EFFECTS: Adds a new patient
    private void doNewPatient() {
        Patient patient;
        String command;

        patient = makePatient();
        if (patient == null) {
            System.out.println("Returning to Menu...");
        } else {
            System.out.println("\nAdmit Patient? (type yes to admit)");
            command = input.next();
            if (command.equals("yes")) {
                doAdmit(patient);
            }
            patients.add(patient);
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds a new doctor
    private void doAddDoctor() {
        String name;
        Doctor doctor;
        String command;
        System.out.println("Please Enter the Doctor's Name");
        name = input.next();
        System.out.println("Create new doctor: " + name + "? (type yes to create, type anything else to cancel)");
        command = input.next();
        if (command.equals("yes")) {
            doctor = new Doctor(name);
            doctors.add(doctor);
        } else {
            System.out.println("Returning to Menu...");
        }
    }

    // EFFECTS: returns the doctor in this.doctors
    private Doctor searchDoctors(String docName) {
        Doctor result = null;
        for (Doctor d : doctors) {
            String name = d.getName();
            if (name.equals(docName)) {
                result = d;
            }
        }
        return result;
    }

    // MODIFIES: this
    // EFFECTS: admits a patient into the hospital
    private void doAdmit(Patient patient) {
        String date;
        String reason;
        Doctor doctor;

        if (patient.getHospitalized()) {
            System.out.println("Patient is already hospitalized");
        } else {
            System.out.println("\nPlease enter the date (dd/mm/year)");
            date = input.next();
            System.out.println("Please enter the reason for hospitalization");
            reason = input.next();
            System.out.println("Please enter the Doctor's name");
            doctor = searchDoctors(input.next());
            admitDoc(doctor, patient, date, reason);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the doctor to the patient and the patient to the doctor
    private void admitDoc(Doctor doctor, Patient patient, String date, String reason) {
        String command = null;
        if (null == doctor) {
            System.out.println("Doctor does not exist, try again? (type yes to try again)");
            command = input.next();
            if (command.equals("yes")) {
                doAdmit(patient);
            } else {
                System.out.println("Returning to menu...");
            }
        } else {
            patient.newHospitalized(date, reason, doctor);
            doctor.addPatient(patient);
            System.out.println("Patient has been hospitalized");
        }
    }

    // MODIFIES: this
    // EFFECTS: in depth sets up the patient
    private Patient makePatient() {
        String name;
        int height;
        int weight;
        String command;
        Patient result;
        result = null;
        System.out.println("\nPlease enter the Patient's name");
        name = input.next();

        System.out.println("\nPlease enter the Patient's height (in cm)");
        height = input.nextInt();

        System.out.println("\nPlease enter the Patient's weight (in kg)");
        weight = input.nextInt();

        System.out.println("\nCreate new patient with name: " + name + ", weight: " + weight + "kg" + ", height: "
                + height + "cm? (type yes to create, type anything else to cancel)");

        command = input.next();

        if (command.equals("yes")) {
            System.out.println("\nCreated New Patient");
            result = new Patient(name, height, weight, id);
            id = id + 1;
        }
        return result;
    }
}
