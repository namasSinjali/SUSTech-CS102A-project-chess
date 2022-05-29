package com;

import com.backend.Game;
import com.backend.piece.Queen;
import com.backend.special_moves.CastleMove;
import com.backend.special_moves.EnPassantMove;
import com.backend.special_moves.PromotionPawnMove;
import com.backend.special_moves.SpecialMove;
import com.gui.Board;
import com.gui.Icons;
import com.gui.Sidebar;
import com.gui.WindowFrame;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class Main {
    private static Board board = new Board();
    private static Game game = new Game();
    public static ArrayList<String> loadGameFile = new ArrayList<>();
    public static WindowFrame window;
    public static String loadFromFile = null;

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
        window = new WindowFrame();
//        WindowFrame window = new WindowFrame();

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


    }



    public static void newGame() {
        game = new Game();

        loadGameFile = new ArrayList<>();
        game.loadChessGame(NEW_GAME_CHESSBOARD());
        board.loadBoard(game.getChessComponents());
        window.updateInterface();
    }

    public static void loadGame(String fileName) {
        game = new Game();
        game.loadChessGame(NEW_GAME_CHESSBOARD());
        board.loadBoard(game.getChessComponents());
        loadGameFile = new ArrayList<>();
        ArrayList<String> tempLoadGame;

        try {
           tempLoadGame = getListCoordinateFromFIle(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!tempLoadGame.isEmpty()) {
            for (String coordinate :
                    tempLoadGame) {

                move(new ChessboardPoint(Character.getNumericValue(coordinate.charAt(1)), Character.getNumericValue(coordinate.charAt(3))), new ChessboardPoint(Character.getNumericValue(coordinate.charAt(6)), Character.getNumericValue(coordinate.charAt(8))));
                System.out.println(coordinate);

            }
        }

        window.updateInterface();


    }

    private final static List<String> NEW_GAME_CHESSBOARD() {
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
        return Collections.unmodifiableList(chessboard);
    }

    public static Board getBoard() {
        return board;
    }

    public static Game getGame() {return game;}

    public static void hint(ChessboardPoint point) {
        ArrayList<ChessboardPoint> points = (ArrayList<ChessboardPoint>) game.getCanMovePoints(point);
        board.hint(points);
    }
/*
    public static List<String> LoadGameSelectedGame(String filename){
        return null;
    }
*/

    public static void move(ChessboardPoint from, ChessboardPoint to) {

        ChessboardPoint move = game.moveChess(from, to);
        if (move == null)
            return;
        board.movePiece(from, to);
        window.updateInterface();

        if (move instanceof SpecialMove) {
            if (move instanceof EnPassantMove) {
                EnPassantMove m = (EnPassantMove) move;
                board.removePiece(m.capturePawn);
            } else if (move instanceof CastleMove) {
                board.movePiece(new ChessboardPoint(move.X, move.Y == 2 ? 0 : 7), new ChessboardPoint(move.X, move.Y == 2 ? 3 : 5));
                board.removePiece(new ChessboardPoint(move.X, move.Y == 2 ? 0 : 7));
            } else if (move instanceof PromotionPawnMove) {
                board.removePiece(to);
                board.addPiece(new Queen(to, game.getOpponentPlayer()));
            }
        }
        loadGameFile.add(from.toString() + to.toString());

    }

    public static ArrayList<String> getListCoordinateFromFIle(String fileName) throws IOException {
        loadFromFile = fileName;
        List<String> chessboard = Files.readAllLines(Paths.get(loadFromFile));
        return (ArrayList<String>) chessboard;
    }

    public static void saveCoordinate(ArrayList<String> listCoordinate) {
        try {

            FileWriter writer = new FileWriter(loadFromFile);
            if (!listCoordinate.isEmpty()) {
                for (String coordinate :
                        listCoordinate) {
                    writer.write(coordinate);
                    writer.write("\n");
                }
            }


            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void resign(ChessColor playerColor){
        Main.loadGameFile.add(playerColor==ChessColor.BLACK?"1-0":"1-0");
        Game.chessNotation.add(playerColor==ChessColor.BLACK?"1-0":"1-0");
        window.updateInterface();

        endGame(playerColor == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE);
    }
    public static void endGame(ChessColor winner){
        if(winner == null){
            JOptionPane.showMessageDialog(Main.window,
                    " DRAW\nStarting new game",
                    "Game Over",
                    JOptionPane.PLAIN_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(Main.window,
                    winner.toString() + " WINS\nStarting new game",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE,
                    winner == ChessColor.BLACK ? Icons.BLACK_KING.getIcon() : Icons.WHITE_KING.getIcon());
        }

        Main.newGame();
    }
}