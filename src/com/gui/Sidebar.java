package com.gui;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    public Sidebar() {
        this.setPreferredSize(new Dimension(700, 700));
        ImageIcon icon = new ImageIcon("src/rook-black.jpg");
        Image i = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(i);
        JLabel label = new JLabel("rook", icon, JLabel.CENTER);
        label.setBackground(Color.green);

        this.add(label);
    }
}
