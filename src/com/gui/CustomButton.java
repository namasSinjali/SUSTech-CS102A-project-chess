package com.gui;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    public CustomButton(String label){
        super(label);
        this.setForeground(Color.WHITE);
        this.setOpaque(true);
        this.setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(WindowFrame.getCurrentTheme().hint);
    }
}
