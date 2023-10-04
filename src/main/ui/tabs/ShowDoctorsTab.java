package ui.tabs;

import model.Doctor;
import ui.HospitalUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//Class for Doctors tab
public class ShowDoctorsTab extends Tab {

    private static final String INIT_GREETING = "Please click on a doctor to see their patients!";
    private JLabel greeting;
    private JLabel reportMsg;
    JPanel showDocs = new JPanel();

    //EFFECTS: constructs the doctors tab
    public ShowDoctorsTab(HospitalUI controller) {
        super(controller);

        setLayout(new GridLayout(4, 1));

        placeGreeting();

        placeAddDoctor();

        reportMsg = new JLabel("");

        add(reportMsg, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: creates the place to add a new doctor
    private void placeAddDoctor() {
        JTextField name = new JTextField(20);
        JLabel label = new JLabel("Name:");
        JButton b1 = new JButton("Add Doctor ");

        JPanel row = new JPanel();
        row.add(label);
        row.add(name);
        row.add(b1);
        this.add(row);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Doctor> doctors = getController().getDoctors();
                String docName = name.getText();
                Doctor newDoc = new Doctor(docName);
                String msg = "Added doctor " + docName;
                reportMsg.setText(msg);
                doctors.add(newDoc);
                updateButtons();
            }
        });
    }

    //EFFECTS: creates msg at top of panel
    private void placeGreeting() {
        greeting = new JLabel(INIT_GREETING, JLabel.CENTER);
        greeting.setSize(WIDTH, HEIGHT / 3);
        this.add(greeting);
    }

    //EFFECTS: creates the buttons for each doctor
    private JPanel placeHomeButtons() {
        List<Doctor> doctors = getController().getDoctors();
        double cielInput = (double) doctors.size() / (double) 4;
        JPanel docButtons = new JPanel();
        int num = (int) Math.ceil(cielInput);
        docButtons.setLayout(new GridLayout(num, 4));


        for (Doctor d : doctors) {
            JButton b1 = new JButton(d.getName());
            docButtons.add(b1);

            b1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    adamsucks(d);
                }
            });

        }

        return docButtons;
    }

    //EFFECTS: opens the new panel for the doctor
    private void adamsucks(Doctor d) {
        JFrame newDocPanel = new DoctorPanel(d);
        newDocPanel.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: creates redraws the buttons.
    public void updateButtons() {
        remove(showDocs);
        showDocs = placeHomeButtons();
        add(showDocs);
        revalidate();
        repaint();
    }
}
