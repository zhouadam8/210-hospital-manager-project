package ui.tabs;

import persistence.*;
import model.Doctor;
import model.Patient;
import ui.HospitalUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Class representation for the Home screen
public class HomeTab extends Tab {
    JButton b1 = new JButton("Add Doctor");
    JButton b2 = new JButton("Add Patient");
    JButton b3 = new JButton("Save Hospital State");
    JButton b4 = new JButton("Load Hospital State");

    private static final String INIT_GREETING = "Welcome to the Hospital!";
    private JLabel greeting;

    //MODIFIES: This
    //EFFECTS: constructs a home tab for console with buttons and a greeting
    public HomeTab(HospitalUI controller) {
        super(controller);

        setLayout(new GridLayout(4, 1));

        placeGreeting();
        placeAddTabs();
    }

    //EFFECTS: creates greeting at top of console
    private void placeGreeting() {
        greeting = new JLabel(INIT_GREETING, JLabel.CENTER);
        greeting.setSize(WIDTH, HEIGHT / 3);
        this.add(greeting);
    }

    //EFFECTS: creates Buttons onto the screen
    private void placeAddTabs() {

        JPanel buttonRow1 = formatButtonRow(b1);
        JPanel buttonRow2 = formatButtonRow(b3);
        buttonRow1.add(b2);
        buttonRow2.add(b4);
        buttonRow1.setSize(WIDTH, HEIGHT / 6);
        buttonRow2.setSize(WIDTH, HEIGHT / 6);
        
        addButtonFunction1(b1, b2);
        addButtonFunction2(b3, b4);

        this.add(buttonRow1);
        this.add(buttonRow2);
    }

    //EFFECTS: Adds functions to the buttons
    private void addButtonFunction2(JButton b3, JButton b4) {

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
                getController().getPatientsTab().updateScroll();
                JFrame newPopUp = new PopUp("State Saved to" + HospitalUI.JSON_STORE);
                newPopUp.setVisible(true);
            }
        });

        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
                JFrame newPopUp = new PopUp("State Loaded from" + HospitalUI.JSON_STORE);
                newPopUp.setVisible(true);
            }
        });
    }

    //EFFECTS: Adds functions to the other buttons
    private void addButtonFunction1(JButton b1, JButton b2) {
        b1.addActionListener(e -> {
            getController().getTabbedPane().setSelectedIndex(HospitalUI.SHOW_DOCTORS_TAB_INDEX);
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                method2();
            }
        });
    }

    //EFFECTS: Reads the JSON file
    private void load() {
        try {
            JsonReader jsonReader = getController().getJsonReader();
            ArrayList<Object> result = jsonReader.read();
            getController().setDoctors((ArrayList<Doctor>) result.get(0));
            getController().setPatients((ArrayList<Patient>) result.get(1));
            getController().setId((int) result.get(2));
            getController().getPatientsTab().updateScroll();
            getController().getDoctorsTab().updateButtons();
            System.out.println("Loaded from " + HospitalUI.JSON_STORE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //EFFECTS: creates a new panel to make a patient
    private void method2() {
        JFrame newPatPanel = new MakeNewPatient(this);
        newPatPanel.setVisible(true);
    }

    //EFFECTS: saves the current state as a JSON file
    private void save() {

        ArrayList<Doctor> docs = (ArrayList<Doctor>) getController().getDoctors();
        ArrayList<Patient> patients = (ArrayList<Patient>) getController().getPatients();
        int id = getController().getId();
        JsonWriter jsonWriter = getController().getJsonWriter();
        try {
            jsonWriter.open();
            jsonWriter.write(docs, patients, id);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
