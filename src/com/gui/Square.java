package com.gui;

import com.ChessboardPoint;

import javax.swing.*;
import java.awt.*;

class Square extends JLabel {
    private final Color normalBgColor;
    private final Color highlightBgColor = new Color(49, 160, 54);
    private final Color hintColor  = new Color(11, 70, 13);
    private Circle hintCircle = new Circle();
    private boolean isHighlighted = false;
    private boolean isEmpty = true;

    protected final ChessboardPoint location;

    public Square(boolean isBlack, int x, int y) {
        normalBgColor = isBlack ? Color.getHSBColor(0.34313726f, 0.17f, 0.78431374f) : Color.getHSBColor(0.3472222f, 0.050632913f, 0.92941177f);

        this.location = new ChessboardPoint(x, y);

        this.setOpaque(true);
        this.setBackground(normalBgColor);

        this.setLayout(new BorderLayout());
        this.add(hintCircle, BorderLayout.CENTER);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
    }

    public void hint(boolean state){
        this.hintCircle.setVisible(state);
    }
    public void highlight(boolean state){
//        this.setBackground(state ? highlightBgColor : normalBgColor);
        this.isHighlighted = state;
        this.repaint();
    }
    public void switchHighlight(){
        this.isHighlighted = !this.isHighlighted;
    }

    public boolean isEmpty(){
        return isEmpty;
    }

    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
        isEmpty = false;
    }
    public Icon removeIcon(){
        Icon i = this.getIcon();
        super.setIcon(null);
        isEmpty = true;

        return i;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBackground(isHighlighted ? highlightBgColor : normalBgColor);
    }

    private class Circle extends JLabel {
        private String state;
        public Circle(){
//            this.setSize(10, 10);
            this.setVisible(false);
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int radius = (int)(this.getWidth() * .15);
            int x = this.getWidth()/2 - radius;
            int y = this.getHeight()/2 - radius;
            g.setColor(hintColor);
            g.fillOval(x, y, 2*radius, 2*radius);
        }
    }

//    @Override
//    public String toString(){
//        return String.format("%c%d", (char)y+'a', x+1);
//    }
}