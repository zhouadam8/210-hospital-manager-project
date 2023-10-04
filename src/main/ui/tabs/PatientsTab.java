package ui.tabs;

import model.Patient;
import ui.HospitalUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Class for Patient Tab
public class PatientsTab extends Tab implements ListSelectionListener {

    private static final String REPORT_GEN_MESSAGE = "Latest report: Today at ";

    private List<Patient> patients;
    private JPanel curPane = new JPanel();
    JList<String> patientNames;
    private JPanel rightPane = new JPanel();

    //REQUIRES: HospitalUI controller that holds this tab
    //EFFECTS: Create a patients tab
    public PatientsTab(HospitalUI controller) {
        super(controller);

        setLayout(new BorderLayout());
        setupButtons();
        this.patients = getController().getPatients();
        updateScroll();
        updateRight(-1);


    }

    //MODIFIES: this
    //EFFECTS: places the buttons on the tab
    private void setupButtons() {
        JButton b1 = new JButton("Admit");
        JButton b2 = new JButton("Release");
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.add(b1);
        buttons.add(b2);
        add(buttons, BorderLayout.SOUTH);

        addButtonFunction(b1, b2);
    }

    //MODIFIES: this
    //EFFECTS: adds the functionality to the buttons
    private void addButtonFunction(JButton b1, JButton b2) {
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = patientNames.getSelectedIndex();
                Patient p = patients.get(index);
                methodExtract(p);

            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = patientNames.getSelectedIndex();
                Patient p = patients.get(index);
                methodExtract2(p);
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: creates the new frame for admitting patients
    private void methodExtract(Patient patient) {
        JFrame newAdmitFrame = new AdmitFrame(this, patient);
        newAdmitFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: creates the new frame for releasing patients
    private void methodExtract2(Patient patient) {
        JFrame newReleaseFrame = new ReleaseFrame(this, patient);
        newReleaseFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: creates the scroll pane on the left for viewing patients
    private JScrollPane leftScroll() {
        List<String> rsf = new ArrayList<>();
        for (Patient p : patients) {
            if (p.getHospitalized()) {
                String name = p.getName() + " - Admitted";
                rsf.add(name);
            } else {
                String name = p.getName() + " - Not Admitted";
                rsf.add(name);
            }
        }
        patientNames = new JList(rsf.toArray());
        patientNames.addListSelectionListener(this);
        patientNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientNames.setSelectedIndex(0);
        patientNames.setVisibleRowCount(10);

        JScrollPane patientScrollPane = new JScrollPane(patientNames);
        patientScrollPane.createVerticalScrollBar();
        patientScrollPane.setHorizontalScrollBar(null);
        return patientScrollPane;
    }

    //MODIFIES: This
    //EFFECTS: Refreshes the patients in the scroll wheel
    public void updateScroll() {
        this.patients = getController().getPatients();
        remove(curPane);
        JLabel label = new JLabel("Patients:");
        JPanel jpnl = new JPanel();
        jpnl.setLayout(new BoxLayout(jpnl, BoxLayout.Y_AXIS));
        jpnl.add(label);
        jpnl.add(leftScroll());
        curPane = jpnl;
        add(jpnl, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    //EFFECTS: Tells to change the information when selecting a new Patient
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            int index = patientNames.getSelectedIndex();
            updateRight(index);
        }
    }

    //EFFECTS: Updates each Patients Information
    public void updateRight(int index) {
        JLabel name = new JLabel("Name: ");
        JLabel weight = new JLabel("Weight: ");
        JLabel height = new JLabel("Height: ");
        JPanel rsf = new JPanel();

        remove(rightPane);
        rsf.setLayout(new BoxLayout(rsf, BoxLayout.Y_AXIS));
        if (-1 == index) {
            rsf.add(name);
            rsf.add(height);
            rsf.add(weight);
        } else {
            Patient p = patients.get(index);
            System.out.println(p.getName());
            JLabel n = new JLabel("Name: " + p.getName());
            JLabel w = new JLabel("Weight: " + p.getWeight());
            JLabel h = new JLabel("Height: " + p.getHeight());
            rsf.add(n);
            rsf.add(h);
            rsf.add(w);
        }
        rightPane = rsf;
        refresh(rsf);
    }

    //EFFECTS: Redraws the right Panel
    public void refresh(JPanel rsf) {
        add(rsf, BorderLayout.EAST);
        revalidate();
        repaint();
    }
}
