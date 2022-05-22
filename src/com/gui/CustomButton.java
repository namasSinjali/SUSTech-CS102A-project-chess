package com.gui;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    public CustomButton(String label){
        super(label);
        this.setBackground(new Color(11, 70, 13));
        this.setForeground(Color.WHITE);
        this.setOpaque(true);
        this.setBorderPainted(false);
    }
}
