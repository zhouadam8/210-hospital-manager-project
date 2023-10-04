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

//CLass for JFrame shown when releasing patient
public class ReleaseFrame extends JFrame {
    PatientsTab patientsTab;
    Patient patient;

    //MODIFIES: patientsTab
    //EFFECT: constructs a panel that tells whether or not a patient can be released
    ReleaseFrame(PatientsTab patientsTab, Patient p) {
        setSize(300, 150);

        setLayout(new BorderLayout());
        this.patientsTab = patientsTab;
        this.patient = p;

        if (!p.getHospitalized()) {
            JLabel jlbl = new JLabel("Patient is Not in the Hospital");
            jlbl.setHorizontalAlignment(SwingConstants.CENTER);
            add(jlbl);
        } else {
            String doc = patient.getDoctor();
            List<Doctor> searchList = patientsTab.getController().getDoctors();
            Doctor searchResult = search(searchList, doc);
            searchResult.removePatient(patient);
            patient.cured("");
            JLabel jlbl = new JLabel("Patient has been released!");
            jlbl.setHorizontalAlignment(SwingConstants.CENTER);
            add(jlbl);
            patientsTab.updateScroll();
        }
    }

    //EFFECTS: returns the doc in the searchList with the same name
    private Doctor search(List<Doctor> searchList, String doc) {
        Doctor rsf = new Doctor("fake");
        for (Doctor d : searchList) {
            if (doc.equals(d.getName())) {
                rsf = d;
            }
        }
        return rsf;
    }
}
