package com.gui;

import com.ChessColor;
import com.Main;
import com.backend.Game;
import com.sun.org.glassfish.gmbal.GmbalException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Sidebar extends JPanel {

    public Sidebar() {
        this.setBackground(Color.getHSBColor(0.3472222f, 0.050632913f, 0.92941177f));
        this.setOpaque(true);
//        this.setPreferredSize(new Dimension(400, 700));
        this.setMinimumSize(new Dimension(200, Integer.MIN_VALUE));
        this.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));
//        Image i = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
//        icon = new ImageIcon(i);

        PlayerPanelContainer panelContainer = new PlayerPanelContainer();
        ButtonContainer buttonContainer = new ButtonContainer();

        MovesLabel movesLabel = new MovesLabel();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        panelContainer.setMaximumSize(new Dimension(400, 200));
        this.add(panelContainer);

        this.add(Box.createVerticalStrut(50));

        buttonContainer.setMaximumSize(new Dimension(200, 80));
        this.add(buttonContainer);

        this.add(Box.createVerticalGlue());

        movesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        movesLabel.setMaximumSize(new Dimension(400, 400));
        this.add(movesLabel);

        this.add(Box.createVerticalGlue());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Component parent = this.getParent();
        int width = parent.getWidth()/4;
        width = Math.max(300, width);
        width = Math.min(500, width);
        this.setPreferredSize(new Dimension(width, parent.getHeight()));
    }
}
class PlayerPanelContainer extends JPanel {
    public PlayerPanelContainer(){
        PlayerPanel whitePanel = new PlayerPanel(ChessColor.WHITE, true);
        PlayerPanel blackPanel = new PlayerPanel(ChessColor.BLACK, false);

        whitePanel.label.setText("White");
        blackPanel.label.setText("Black");

        this.setOpaque(false);
        this.setLayout(new GridLayout(1, 2));
        this.add(whitePanel);
        this.add(blackPanel);
    }
}
class PlayerPanel extends JPanel implements ActionListener {
    final ChessColor playerColor;
    private boolean isActive;
    final JLabel label = new JLabel();
    final JButton btnResign = new CustomButton("Resign");

    public PlayerPanel(ChessColor playerColor, boolean isActive){
        this.playerColor = playerColor;
        setActive(isActive);

        label.setSize(JLabel.WIDTH, 10);
        label.setVerticalAlignment(SwingConstants.CENTER);

        btnResign.addActionListener(this);

        this.setBorder(new EmptyBorder(10,10,10,10));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnResign.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(label);
        this.add(Box.createVerticalStrut(10));
        this.add(btnResign);

        this.setBackground(Color.getHSBColor(0.34313726f, 0.17f, 0.78431374f));

        btnResign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, playerColor==ChessColor.BLACK?"WHITE WINS":"BLACK WINS");
                Main.loadGameFile.add(playerColor==ChessColor.BLACK?"1-0":"0-0");
                Game.chessNotation.add(playerColor==ChessColor.BLACK?"1-0":"0-0");
            }
        });

      }

    @Override
    public void actionPerformed(ActionEvent e) {
//                Main.resign(playerColor);
        setActive(!isActive);
    }

    public void setActive(boolean state) {
        isActive = state;
        if(state){
            this.setOpaque(true);
            label.setFont(label.getFont().deriveFont(Font.BOLD));
        } else {
            this.setOpaque(false);
            label.setFont(label.getFont().deriveFont(Font.PLAIN));
        }
        repaint();
    }
}

class ButtonContainer extends JPanel {
    public ButtonContainer() {
        JButton newGameBtn = new CustomButton("New Game");
        JButton loadGameBtn = new CustomButton(("Load Game"));
        JButton saveGameBtn = new CustomButton(("Save Game"));
        JButton saveAsGameBtn = new CustomButton(("Save As Game"));
        this.setLayout(new GridLayout(4, 1, 10, 5));
        this.setPreferredSize(new Dimension(0, 100));
        this.add(newGameBtn);
        this.add(loadGameBtn);
        this.add(saveGameBtn);
        this.add(saveAsGameBtn);

        newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.newGame();
                Main.loadFromFile = null;
            }
        });
        loadGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();


                int returnValue;
                fileChooser.setFileFilter(new FileNameExtensionFilter("java chess file", "bn"));
                returnValue = fileChooser.showOpenDialog(null);
                while (!fileChooser.getSelectedFile().getAbsolutePath().endsWith(".bn")) {
                    JOptionPane.showMessageDialog(null, "Please choose \".bn\" file!");
                    fileChooser.setFileFilter(new FileNameExtensionFilter("java chess file", "bn"));
                    returnValue = fileChooser.showOpenDialog(null);
                }
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    Main.loadGame(selectedFile.getAbsolutePath());
                }
            }
        });
        saveGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Main.loadFromFile!=null){
                    Main.saveCoordinate(Main.loadGameFile);
                }else{
                    saveGameAs();
                }
            }
        });
        saveAsGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (String tile :
                        Main.loadGameFile) {
                    System.out.println(tile);
                }
                saveGameAs();

            }
        });
    }
    private void saveGameAs(){
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new FileNameExtensionFilter("java chess file", "bn"));
        int status = fileChooser.showSaveDialog(Main.window);

        if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = null;
            try {
                fileName = selectedFile.getCanonicalPath();
                System.out.println(fileName);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (!fileName.endsWith(".bn")) {
                selectedFile = new File(fileName + ".bn");
                try {
                    System.out.println(selectedFile.createNewFile());
                    Main.loadFromFile = selectedFile.getAbsolutePath();
                    Main.saveCoordinate(Main.loadGameFile);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
    }


}

class MovesLabel extends JPanel {
    final JTextArea textArea = new JTextArea();
    public MovesLabel() {
        textArea.setText("1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 Nf6 5. O-O Be7 6. Re1 b5 7. Bb3 d6 ");
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setBackground(Color.getHSBColor(0.34313726f, 0.17f, 0.78431374f));
        textArea.setForeground(new Color(11, 70, 13));
        textArea.setBorder(new EmptyBorder(5,5,5,5));

        this.setPreferredSize(new Dimension(350, 200));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setLayout(new BorderLayout());
        this.add(textArea, BorderLayout.CENTER);
        this.setOpaque(true);
    }
}