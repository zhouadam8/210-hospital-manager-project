package ui.tabs;

import model.Doctor;
import model.Patient;
import ui.HospitalUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

//Class representation for the frame used to admit a patient into the hospital
public class AdmitFrame extends JFrame {
    JList<String> doctorNames;
    PatientsTab patientsTab;
    Patient patient;

    //EFFECTS: creates a new frame to admit a patient into the hospital
    AdmitFrame(PatientsTab patientsTab, Patient p) {
        setSize(300, 150);

        setLayout(new BorderLayout());
        this.patientsTab = patientsTab;
        this.patient = p;

        if (p.getHospitalized()) {
            JLabel jlbl = new JLabel("Patient Already Hospitalized");
            jlbl.setHorizontalAlignment(SwingConstants.CENTER);
            add(jlbl);
        } else {
            setupButtons();
        }
    }

    //EFFECTS: sets up the buttons and the doctors names
    private void setupButtons() {
        List<Doctor> docs = patientsTab.getController().getDoctors();
        List<String> rsf = new ArrayList<>();
        for (Doctor d: docs) {
            String name = d.getName();
            rsf.add(name);
        }
        setupScroll(rsf);
        JButton jbton = new JButton("Admit Patient");
        JLabel jlbl = new JLabel("Select a Doctor:");
        jlbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(jlbl, BorderLayout.NORTH);
        add(jbton, BorderLayout.SOUTH);

        jbton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Doctor doc = patientsTab.getController().getDoctors().get(doctorNames.getSelectedIndex());
                patient.newHospitalized("", "", doc);
                doc.addPatient(patient);
                patientsTab.updateScroll();
                dispose();
            }
        });
    }

    //EFFECTS: creates a scroll pane for all the doctors in the hospital
    private void setupScroll(List<String> rsf) {
        doctorNames = new JList(rsf.toArray());
        doctorNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        doctorNames.setSelectedIndex(0);
        doctorNames.setVisibleRowCount(10);

        JScrollPane docScrollPane = new JScrollPane(doctorNames);
        docScrollPane.createVerticalScrollBar();
        docScrollPane.setHorizontalScrollBar(null);
        add(docScrollPane);
    }
}
