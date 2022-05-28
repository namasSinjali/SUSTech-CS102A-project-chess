package com.gui;

import com.ChessColor;
import com.ChessboardPoint;
import com.Main;
import com.backend.piece.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Board extends JPanel {
    private Square[][] squares = new Square[8][8];
    private Square selectedSquare;
    private Square hoverSquare;
    private ArrayList<Square> hintSquares = new ArrayList<>();

    public Board() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.setMinimumSize(new Dimension(500, 500));
        this.setOpaque(true);
        this.setAlignmentY(CENTER_ALIGNMENT);

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(8, 8));
        for (int i = 0; i < 8; i++) {
            for (char j = 0; j < 8; j++) {
                Square l = new Square((i + j) % 2 == 1, i, j);
                this.squares[i][j] = l;
                l.addMouseListener(listener);
                grid.add(l);
            }
        }

        JPanel rankLabels = new JPanel();
        rankLabels.setLayout(new GridLayout(8, 1));
        for (int i = 8; i >= 1; i--) {
            JLabel l = new JLabel(String.valueOf(i));
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.CENTER);
            rankLabels.add(l);
        }

        JPanel fileLabels = new JPanel();
        fileLabels.setLayout(new GridLayout(1, 8));
        for (char i = 'a'; i <= 'h'; i++) {
            JLabel l = new JLabel(String.valueOf(i));
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.CENTER);
            fileLabels.add(l);
        }

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = .07;
        c.weighty = .93;
        this.add(rankLabels, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = .93;
        c.weighty = .93;
        this.add(grid, c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = .93;
        c.weighty = .07;
        this.add(fileLabels, c);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getParent().getWidth();
        int height = getParent().getHeight();

        int side = (width < height) ? width : height;

        this.setPreferredSize(new Dimension(side, side));
//        this.setLocation((width-side)/2, (height-side)/2);
    }

    public void loadBoard(Piece[][] chessBoard){
        emptyBoard();
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                addPiece(chessBoard[i][j]);
            }
        }
    }
    public void emptyBoard(){
        removeHint();
        for(Square[] row : squares){
            for(Square s : row){
                s.removeIcon();
            }
        }
    }
    public void addPiece(Piece piece){
        if(piece == null) return;

        ChessColor color = piece.getChessColor();
        Icons icon = null;
        if(piece instanceof Pawn)
            icon = color == ChessColor.BLACK ? Icons.BLACK_PAWN : Icons.WHITE_PAWN;
        else if(piece instanceof Rook)
            icon = color == ChessColor.BLACK ? Icons.BLACK_ROOK : Icons.WHITE_ROOK;
        else if(piece instanceof Bishop)
            icon = color == ChessColor.BLACK ? Icons.BLACK_BISHOP : Icons.WHITE_BISHOP;
        else if(piece instanceof Knight)
            icon = color == ChessColor.BLACK ? Icons.BLACK_KNIGHT : Icons.WHITE_KNIGHT;
        else if(piece instanceof Queen)
            icon = color == ChessColor.BLACK ? Icons.BLACK_QUEEN : Icons.WHITE_QUEEN;
        else if(piece instanceof King)
            icon = color == ChessColor.BLACK ? Icons.BLACK_KING : Icons.WHITE_KING;

        if(icon != null)
            squares[piece.getLocation().X][piece.getLocation().Y].setIcon(icon.getIcon());
    }
    public void movePiece(ChessboardPoint from, ChessboardPoint to){
        squares[to.X][to.Y].setIcon(squares[from.X][from.Y].removeIcon());
    }
    public void removePiece(ChessboardPoint source) {
        squares[source.X][source.Y].removeIcon();
    }

    public void hint(ArrayList<ChessboardPoint> points){
        removeHint();
        hintSquares = new ArrayList<>();

        for(ChessboardPoint point : points){
            Square s = squares[point.X][point.Y];
            hintSquares.add(s);
            s.hint(true);
        }
    }
    public void removeHint(){
        for(Square square : hintSquares){
            square.hint(false);
        }
        hintSquares = new ArrayList<>();
    }

    public void select(Square s){
        removeSelection();
        s.highlight(true);
        selectedSquare = s;
        hoverSquare = null;
        Main.hint(selectedSquare.location);
    }
    public void removeSelection(){
        if(selectedSquare == null) return;

        selectedSquare.highlight(false);
        selectedSquare = null;
        removeHint();
    }
    final MouseListener listener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Square source = (Square)e.getSource();

//            if(source == selectedSquare) {
//                selectedSquare.highlight(false);
//                selectedSquare = null;
//                removeHint();
//                return;
//            }
//            if(selectedSquare != null)
//                selectedSquare.highlight(false);
//
//            source.highlight(true);
//            selectedSquare = source;
//            com.Main.hint(new com.ChessboardPoint(source.x, source.y));
            if(hintSquares.contains(source)){
                Main.move(selectedSquare.location, source.location);
                removeSelection();
                removeHint();
                return;
            }

            if(source.isEmpty() || source == selectedSquare)
                removeSelection();
            else
                select(source);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Square source = (Square)e.getSource();

            if(source == selectedSquare) return;
            source.highlight(true);
            hoverSquare = source;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Square source = (Square)e.getSource();
            if (hoverSquare == null) return;
            hoverSquare.highlight(false);
        }
    };
}