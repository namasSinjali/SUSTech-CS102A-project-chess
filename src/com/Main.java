package com;

import com.backend.Game;
import com.gui.Board;
import com.gui.Sidebar;
import com.gui.WindowFrame;

import javax.swing.*;
import java.util.*;

public class Main {
    private static Board board = new Board();
    private static Game game = new Game();

    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frame.setLayout(new BorderLayout());
//
//        JPanel sidebar = new Sidebar();
//
//        frame.add(board, BorderLayout.CENTER);
//        frame.add(sidebar, BorderLayout.EAST);
//        frame.setVisible(true);

        WindowFrame window = new WindowFrame();

        ArrayList<String> chessboard = new ArrayList<>();
        chessboard.add("R_BQK___");
        chessboard.add("PPP__PP_");
        chessboard.add("__NPP_n_");
        chessboard.add("___Np___");
        chessboard.add("___p____");
        chessboard.add("__p_____");
        chessboard.add("pp___pp_");
        chessboard.add("rnb_kb_R");
        chessboard.add("w");

        game.loadChessGame(chessboard);
        board.loadBoard(game.getChessComponents());
    }

    public static void newGame(){
        game = new Game();
        ArrayList<String> chessboard = new ArrayList<>();
        chessboard.add("RNBKQBNR");
        chessboard.add("PPPPPPPP");
        chessboard.add("________");
        chessboard.add("________");
        chessboard.add("________");
        chessboard.add("________");
        chessboard.add("pppppppp");
        chessboard.add("rnbkqbnr");
        chessboard.add("w");
        game.loadChessGame(chessboard);
        board.loadBoard(game.getChessComponents());
    }

    public static Board getBoard(){
        return board;
    }

    public static void hint(ChessboardPoint point) {
        ArrayList<ChessboardPoint> points = (ArrayList<ChessboardPoint>) game.getCanMovePoints(point);
        board.hint(points);
    }

    public static void move(ChessboardPoint from, ChessboardPoint to) {
        if(game.moveChess(from, to)){
            board.movePiece(from, to);
        }
    }
}