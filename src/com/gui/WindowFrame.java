package com.gui;


import com.Main;

import javax.swing.*;
import java.awt.*;

public class WindowFrame extends JFrame {
    Sidebar sidebar = new Sidebar();

    public WindowFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(900, 650));

        this.setLayout(new BorderLayout(10, 10));

        JPanel boardContainer = new JPanel();
        boardContainer.add(Main.getBoard());

        this.add(boardContainer, BorderLayout.CENTER);
        this.add(sidebar, BorderLayout.EAST);
        this.setVisible(true);
    }
    public void updateInterface(){
        sidebar.updateInterface();
    }
}