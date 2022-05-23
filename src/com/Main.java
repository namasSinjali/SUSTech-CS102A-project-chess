package com;

import com.backend.Game;
import com.backend.piece.Queen;
import com.backend.special_moves.CastleMove;
import com.backend.special_moves.EnPassantMove;
import com.backend.special_moves.PromotionPawnMove;
import com.backend.special_moves.SpecialMove;
import com.gui.Board;
import com.gui.Sidebar;
import com.gui.WindowFrame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

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

        /*ArrayList<String> chessboard = new ArrayList<>();
        chessboard.add("R_BQK___");
        chessboard.add("PPP__PP_");
        chessboard.add("__NPP_n_");
        chessboard.add("___Np___");
        chessboard.add("___p____");
        chessboard.add("__p_____");
        chessboard.add("pp___pp_");
        chessboard.add("rnb_kb_R");
        chessboard.add("w");*/

        game.loadChessGame(NEW_GAME_CHESSBOARD());
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
        List<String> loadGameFile;
        try {
            loadGameFile = getListCoordinateFromFIle();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!loadGameFile.isEmpty()){
            for (String coordinate:
                 loadGameFile) {

                move(new ChessboardPoint(Character.getNumericValue(coordinate.charAt(1)),Character.getNumericValue(coordinate.charAt(3))),new ChessboardPoint(Character.getNumericValue(coordinate.charAt(6)),Character.getNumericValue(coordinate.charAt(8))));
            }
        }

    }
    private final static List<String> NEW_GAME_CHESSBOARD(){
        List<String> chessboard = new ArrayList<>();
        chessboard.add("RNBQKBNR");
        chessboard.add("PPPPPPPP");
        chessboard.add("________");
        chessboard.add("________");
        chessboard.add("________");
        chessboard.add("________");
        chessboard.add("pppppppp");
        chessboard.add("rnbqkbnr");
        chessboard.add("w");
        return  Collections.unmodifiableList(chessboard);
    }
    public static Board getBoard(){
        return board;
    }

    public static void hint(ChessboardPoint point) {
        ArrayList<ChessboardPoint> points = (ArrayList<ChessboardPoint>) game.getCanMovePoints(point);
        board.hint(points);
    }
    public static List<String> LoadGameSelectedGame(String filename){
        return null;
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
            }else if (move instanceof PromotionPawnMove){
                board.removePiece(to);
                board.addPiece(new Queen(to,game.getOpponentPlayer()));
            }
        }
        writeFile(from,to);

    }
    public static List<String> getListCoordinateFromFIle() throws IOException {
        List<String> chessboard = Files.readAllLines(Paths.get(loadFromFile));
        return chessboard;
    }
    public static void writeFile(ChessboardPoint from, ChessboardPoint to){
        try {

            List<String> chessboard = getListCoordinateFromFIle();
            FileWriter writer = new FileWriter(loadFromFile);
            if (!chessboard.isEmpty()){
                for (String coordinate:
                        chessboard) {
                    writer.write(coordinate);
                    writer.write("\n");
                }
            }
            writer.write(from.toString());
            writer.write(to.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}