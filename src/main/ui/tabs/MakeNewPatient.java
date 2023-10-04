package ui.tabs;

import model.Patient;
import ui.HospitalUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//Class for the Panel used to make a new Patient
public class MakeNewPatient extends JFrame {
    HomeTab homeTab;

    //EFFECTS: Creates a new tab to make a patient
    public MakeNewPatient(HomeTab homeTab) {
        setSize(HospitalUI.WIDTH, HospitalUI.HEIGHT);

        setLayout(new GridLayout(4, 1));
        this.homeTab = homeTab;

        setupButtons();
    }

    //EFFECTS: creates Buttons and labels onto the screen
    public void setupButtons() {
        JTextField name = new JTextField(20);
        JLabel label = new JLabel("Name: ");
        JTextField weight = new JTextField(20);
        JLabel wlabel = new JLabel("Weight(kg): ");
        JTextField height = new JTextField(20);
        JLabel hlabel = new JLabel("Height(cm): ");
        JButton b1 = new JButton("Add Patient ");

        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel row3 = new JPanel();
        row1.add(label);
        row1.add(name);
        row2.add(wlabel);
        row2.add(weight);
        row3.add(hlabel);
        row3.add(height);
        this.add(row1);
        this.add(row2);
        this.add(row3);
        this.add(b1);

        addButtonAction(b1, name, weight, height);
    }

    //EFFECTS: Adds the functions to the buttons
    private void addButtonAction(JButton b1, JTextField name, JTextField weight,JTextField height) {
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Patient> patients = homeTab.getController().getPatients();
                String patientName = name.getText();
                int pheight = Integer.parseInt(height.getText());
                int pweight = Integer.parseInt(weight.getText());
                Patient newPat = new Patient(patientName, pheight, pweight, homeTab.getController().getId());
                int newId = homeTab.getController().getId() + 1;
                homeTab.getController().setId(newId);
                patients.add(newPat);
                homeTab.getController().getPatientsTab().updateScroll();
                dispose();
            }
        });
    }
}
