package com.gui;

import com.ChessColor;
import com.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        this.setLayout(new GridLayout(2, 1, 10, 5));
        this.setPreferredSize(new Dimension(0, 100));
        this.add(newGameBtn);
        this.add(loadGameBtn);

        newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.newGame();
            }
        });
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