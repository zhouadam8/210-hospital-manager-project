package ui.tabs;

import ui.HospitalUI;

import javax.swing.*;
import java.awt.*;

public abstract class Tab extends JPanel {

    private final HospitalUI controller;

    //REQUIRES: HospitalUI controller that holds this tab
    public Tab(HospitalUI controller) {
        this.controller = controller;
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    //EFFECTS: returns the HospitalUI controller for this tab
    public HospitalUI getController() {
        return controller;
    }

}
