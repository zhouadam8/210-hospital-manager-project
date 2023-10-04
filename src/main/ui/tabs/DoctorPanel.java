package ui.tabs;

import model.Doctor;
import model.Patient;
import ui.HospitalUI;

import javax.print.Doc;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Class representation of the JFrame for each Doctor
public class DoctorPanel extends JFrame {
    private Doctor doctor;
    private List<Patient> patients;
    JPanel curState;

    public DoctorPanel(Doctor doctor) {
        super("Doctor Console");
        this.doctor = doctor;
        this.patients = doctor.getPatients();
        setSize(HospitalUI.WIDTH, HospitalUI.HEIGHT);

        setLayout(new BorderLayout());

        JLabel jlbl = new JLabel(doctor.getName());

        jlbl.setFont(new Font("Arial", Font.PLAIN, 15));

        jlbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(jlbl, BorderLayout.NORTH);

        ImageIcon img = new ImageIcon("data/image/doctor icon (1).png");
        this.add(new JLabel(img), BorderLayout.WEST);

        curState = setupEast(setupPatientScroll());

        this.add(curState);
    }

    //EFFECTS: Creates a scroll pane to see all the patients
    private JScrollPane setupPatientScroll() {
        List<String> rsf = new ArrayList<>();
        for (Patient p : patients) {
            String name = p.getName();
            rsf.add(name);
        }
        JList<String> patientNames = new JList(rsf.toArray());
        patientNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientNames.setSelectedIndex(0);
        patientNames.setVisibleRowCount(10);

        JScrollPane patientScrollPane = new JScrollPane(patientNames);
        patientScrollPane.createVerticalScrollBar();
        patientScrollPane.setHorizontalScrollBar(null);

        return patientScrollPane;
    }

    //EFFECTS: setup the labels to the JScrollPane returns a JPanel
    private JPanel setupEast(JScrollPane scroll) {

        JPanel jpnl = new JPanel();
        jpnl.setLayout(new FlowLayout());

        ButtonGroup group = new ButtonGroup();

        JLabel label = new JLabel("Patients:");
        JPanel result = new JPanel();
        result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
        result.add(jpnl);
        result.add(label);
        result.add(scroll);

        return result;
    }
}
