package ui.tabs;

import javax.swing.*;
import java.awt.*;

//Class for a Popup window
public class PopUp extends JFrame {
    String popText;

    //EFFECT: creates a pop up window with the given text
    public PopUp(String popText) {
        this.popText = popText;
        setSize(300, 100);

        setLayout(new BorderLayout());

        JLabel jlbl = new JLabel(popText);
        jlbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(jlbl);
    }
}
