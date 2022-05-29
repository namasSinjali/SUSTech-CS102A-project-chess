package com.gui;

import javax.swing.*;
import java.awt.*;

public enum Icons {
    WHITE_PAWN("white_pawn.png"),
    WHITE_ROOK("white_rook.png"),
    WHITE_BISHOP("white_bishop.png"),
    WHITE_KNIGHT("white_knight.png"),
    WHITE_KING("white_king.png"),
    WHITE_QUEEN("white_queen.png"),
    BLACK_PAWN("black_pawn.png"),
    BLACK_ROOK("black_rook.png"),
    BLACK_BISHOP("black_bishop.png"),
    BLACK_KNIGHT("black_knight.png"),
    BLACK_KING("black_king.png"),
    BLACK_QUEEN("black_queen.png");

    private final ImageIcon icon;
    private ImageIcon iconResized;
    private static final String path = "src/com/gui/images/";
    Icons(String location){
        this.icon = new ImageIcon(path + location);
        this.iconResized = this.icon;
    }

    public ImageIcon getIcon(){
        return icon;
    }
    private ImageIcon getOriginalIcon(){
        return icon;
    }
    private void setIcon(ImageIcon icon){
        this.iconResized = icon;
    }
    static void resizeIcons(int width) {
        for(Icons piece : Icons.values()){
            Image image = piece.getOriginalIcon().getImage();
            Image newImg = image.getScaledInstance(width, width,  java.awt.Image.SCALE_SMOOTH);

            piece.setIcon(new ImageIcon(newImg));
        }
    }
}