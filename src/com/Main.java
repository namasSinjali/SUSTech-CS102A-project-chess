package com;

import com.backend.Game;
import com.backend.piece.Queen;
import com.backend.special_moves.CastleMove;
import com.backend.special_moves.EnPassantMove;
import com.backend.special_moves.PromotionMove;
import com.backend.special_moves.SpecialMove;
import com.gui.Board;
import com.gui.Sidebar;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Main {
    private static Board board = new Board();
    private static Game game = new Game();

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

//        com.gui.Board board = new com.gui.Board();
        JPanel sidebar = new Sidebar();

        frame.add(board, BorderLayout.CENTER);
        frame.add(sidebar, BorderLayout.EAST);
        frame.setVisible(true);

//        com.backend.ConcreteChessGame game = new com.backend.ConcreteChessGame();

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

    public static void hint(ChessboardPoint point) {
        ArrayList<ChessboardPoint> points = (ArrayList<ChessboardPoint>) game.getCanMovePoints(point);
        board.hint(points);
    }

    public static void move(ChessboardPoint from, ChessboardPoint to) {
        ChessboardPoint move = game.moveChess(from, to);
        if(move == null)
            return;
        board.movePiece(from, to);

        if(move instanceof SpecialMove){
            if(move instanceof EnPassantMove){
                EnPassantMove m = (EnPassantMove) move;
                board.removePiece(m.capturePawn);
            }else if (move instanceof CastleMove){
                board.movePiece(new ChessboardPoint(move.X,move.Y==2?0:7),new ChessboardPoint(move.X,move.Y==2?3:5));
                board.removePiece(new ChessboardPoint(move.X,move.Y==2?0:7));
            }else if (move instanceof PromotionMove){
                board.removePiece(to);
                board.addPiece(new Queen(to,game.getOpponentPlayer()));
            }
        }

    }
}