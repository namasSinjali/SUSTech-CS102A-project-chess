package com.gui;


import com.Main;

import javax.swing.*;
import java.awt.*;

public class WindowFrame extends JFrame {
    private Sidebar sidebar = new Sidebar();

    private static Theme currentTheme = Theme.GREEN;
    public WindowFrame(){
        this.setIconImage(Icons.WHITE_KNIGHT.getIcon().getImage());
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
    public static Theme getCurrentTheme(){
        return currentTheme;
    }
    public static void setCurrentTheme(Theme theme){
        if(currentTheme == theme) return;
        currentTheme = theme;
        Main.window.repaint();
    }
}