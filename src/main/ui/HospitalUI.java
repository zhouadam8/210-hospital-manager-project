package ui;

import model.*;

import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.*;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

//Class representation of the UI for hospital
public class HospitalUI extends JFrame {
    public static final int HOME_TAB_INDEX = 0;
    public static final int SHOW_DOCTORS_TAB_INDEX = 1;
    public static final int SHOW_PATIENTS_TAB_INDEX = 2;
    public static final int ADD_DOCTORS_TAB_INDEX = 3;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private JTabbedPane sidebar;
    private List<Doctor> doctors = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();
    private int id;
    public static final String JSON_STORE = "./data/hospital.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);
    JPanel homeTab = new HomeTab(this);
    JPanel patientsTab = new PatientsTab(this);
    JPanel doctorsTab = new ShowDoctorsTab(this);

    //MODIFIES: this
    //EFFECTS: creates HosptialUI, displays sidebar and tabs, intializes doctors and patients and id.
    public HospitalUI() {
        super("Hospital Console");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);

        loadTabs();

        add(sidebar);

        setVisible(true);
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public JsonReader getJsonReader() {
        return jsonReader;
    }

    public JsonWriter getJsonWriter() {
        return jsonWriter;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public int getId() {
        return id;
    }

    public void setDoctors(List<Doctor> docs) {
        this.doctors = docs;
    }

    public void setPatients(List<Patient> pats) {
        this.patients = pats;
    }

    public void setId(int id) {
        this.id = id;
    }

    //MODIFIES: this
    //EFFECTS: adds home tab, Doctors tab and Patients tab to this UI
    private void loadTabs() {
        sidebar.add(homeTab, HOME_TAB_INDEX);
        sidebar.setTitleAt(HOME_TAB_INDEX, "Home");
        sidebar.add(doctorsTab, SHOW_DOCTORS_TAB_INDEX);
        sidebar.setTitleAt(SHOW_DOCTORS_TAB_INDEX, "Doctors");
        sidebar.add(patientsTab, SHOW_PATIENTS_TAB_INDEX);
        sidebar.setTitleAt(SHOW_PATIENTS_TAB_INDEX, "Patients");
    }

    //EFFECTS: returns sidebar of this UI
    public JTabbedPane getTabbedPane() {
        return sidebar;
    }

    public ShowDoctorsTab getDoctorsTab() {
        return (ShowDoctorsTab) doctorsTab;
    }

    public PatientsTab getPatientsTab() {
        return (PatientsTab) patientsTab;
    }
}