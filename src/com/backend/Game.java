package com.backend;

import com.ChessColor;
import com.ChessboardPoint;
import com.backend.piece.*;
import com.backend.special_moves.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Piece[][] pieces = new Piece[8][8];
    public static ArrayList<String> chessNotation = new ArrayList<>();
    private static ChessColor currentPlayer;
    private static Pawn enPassantTarget;
    public static boolean isItEndGame = false;
    public static ChessColor whoWins = null;
    public void setGameToDefault(){
        Game.chessNotation = new ArrayList<>();
        whoWins = null;
        isItEndGame = false;
    }
    public Game() {
        pieces = new Piece[8][8];
        Piece.setChessBoard(this.pieces);
    }

    public static Piece[][] getChessComponents() {
        return pieces;
    }

    public void loadChessGame(List<String> chessboard) {
        for (int i = 0; i < 8; i++) {
            String row = chessboard.get(i);
            for (int j = 0; j < 8; j++) {
                Piece component;

                switch (row.charAt(j)) {
                    case 'R':
                        component = new Rook(new ChessboardPoint(i, j), ChessColor.BLACK);
                        break;
                    case 'r':
                        component = new Rook(new ChessboardPoint(i, j), ChessColor.WHITE);
                        break;
                    case 'N':
                        component = new Knight(new ChessboardPoint(i, j), ChessColor.BLACK);
                        break;
                    case 'n':
                        component = new Knight(new ChessboardPoint(i, j), ChessColor.WHITE);
                        break;
                    case 'B':
                        component = new Bishop(new ChessboardPoint(i, j), ChessColor.BLACK);
                        break;
                    case 'b':
                        component = new Bishop(new ChessboardPoint(i, j), ChessColor.WHITE);
                        break;
                    case 'Q':
                        component = new Queen(new ChessboardPoint(i, j), ChessColor.BLACK);
                        break;
                    case 'q':
                        component = new Queen(new ChessboardPoint(i, j), ChessColor.WHITE);
                        break;
                    case 'K':
                        component = new King(new ChessboardPoint(i, j), ChessColor.BLACK);
                        break;
                    case 'k':
                        component = new King(new ChessboardPoint(i, j), ChessColor.WHITE);
                        break;
                    case 'P':
                        component = new Pawn(new ChessboardPoint(i, j), ChessColor.BLACK);
                        break;
                    case 'p':
                        component = new Pawn(new ChessboardPoint(i, j), ChessColor.WHITE);
                        break;
                    default:
                        component = null;
                        break;
                }

                this.pieces[i][j] = component;
            }
        }

        if (chessboard.get(8).equals("w"))
            currentPlayer = ChessColor.WHITE;
        else
            currentPlayer = ChessColor.BLACK;
    }

    public static ChessColor getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessColor getOpponentPlayer() {
        if (getCurrentPlayer() == ChessColor.BLACK) return ChessColor.WHITE;
        return ChessColor.BLACK;
    }

    public String getChessboardGraph() {
        StringBuilder graph = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                graph.append(pieces[i][j].toString());
            }
            graph.append('\n');
        }
        graph.deleteCharAt(graph.length() - 1);
        return graph.toString();
    }

    public String getCapturedChess(ChessColor player) {
        int k = 1, q = 1, r = 2, b = 2, n = 2, p = 8;

        for (Piece[] row : pieces) {
            for (Piece component : row) {
                if (component.getChessColor() != player) continue;

                if (component instanceof Pawn)
                    p--;
                else if (component instanceof Rook)
                    r--;
                else if (component instanceof Bishop)
                    b--;
                else if (component instanceof Knight)
                    n--;
                else if (component instanceof Queen)
                    q--;
                else if (component instanceof King)
                    k--;
            }
        }

        StringBuilder capturedString = new StringBuilder();
        String c = (player == ChessColor.BLACK) ? "KQRBNP" : "kqrbnp";

        if (k != 0)
            capturedString
                    .append(c.charAt(0))
                    .append(' ')
                    .append(k)
                    .append('\n');

        if (q != 0)
            capturedString
                    .append(c.charAt(1))
                    .append(' ')
                    .append(q)
                    .append('\n');

        if (r != 0)
            capturedString
                    .append(c.charAt(2))
                    .append(' ')
                    .append(r)
                    .append('\n');

        if (b != 0)
            capturedString
                    .append(c.charAt(3))
                    .append(' ')
                    .append(b)
                    .append('\n');

        if (n != 0)
            capturedString
                    .append(c.charAt(4))
                    .append(' ')
                    .append(n)
                    .append('\n');

        if (p != 0)
            capturedString
                    .append(c.charAt(5))
                    .append(' ')
                    .append(p)
                    .append('\n');

        return capturedString.toString();
    }

    public Piece getChess(int x, int y) {
        return pieces[x][y];
    }

    public static Piece getChess(ChessboardPoint source) {
        return pieces[source.X][source.Y];
    }

    public static List<ChessboardPoint> getCanMovePoints(ChessboardPoint source) {
        Piece chess = getChess(source);
        if (chess == null) return new ArrayList<>();

        if (chess.getChessColor() != currentPlayer) return new ArrayList<>();
        List<ChessboardPoint> rawCanMovePoints = chess.getCanMovePoints();

        if (enPassantTarget != null && chess instanceof Pawn) {
            ChessboardPoint targetLocation = enPassantTarget.getLocation();
            if (targetLocation.X == source.X && (source.Y - targetLocation.Y == -1 || source.Y - targetLocation.Y == 1)) {
                rawCanMovePoints.add(new EnPassantMove(targetLocation.offset(-enPassantTarget.direction, 0), targetLocation));
            }
        }


        List<ChessboardPoint> canMovePoints = new ArrayList<>();
        for (ChessboardPoint candidateCoordinate :
                rawCanMovePoints) {
            if (!Utils.isKingCheck(getChessComponents(), candidateCoordinate, chess)) {
                canMovePoints.add(candidateCoordinate);
            }
        }

        return canMovePoints;
    }
    public static Boolean disambiguation(Piece[][] board, Piece selectedPiece,ChessboardPoint target){
        int a = 0;
        ArrayList<Piece> listOfDisambiguationPiece = new ArrayList<>();
        for (Piece[] pieces1 : board) {
            for (Piece piece : pieces1) {
                if (piece!=null && selectedPiece.toString().matches(piece.toString())) {
                    listOfDisambiguationPiece.add(piece);
                }
            }
        }
        for(Piece piece: listOfDisambiguationPiece){
            if (getMove(getCanMovePoints(piece.getLocation()),target)!=null)a++;
        }
        if (a!=1)return true;
        return false;
    }
    public static String getNotation(ChessboardPoint toPoint, ChessboardPoint sourcePoint, Piece source, Piece target, boolean isKingCheck,boolean isDisambiguation) {
        char colS = (char) (97 + sourcePoint.Y);
        char colT = (char) (97 + toPoint.Y);
        int rowT = 8 - toPoint.X;
        //T= target S=source
        String notation = "";
        if (source instanceof Pawn) {
            if (toPoint instanceof EnPassantMove) {
                notation = String.format("%cx%c%d", colS, colT, rowT);
            } else {
                if (target == null) {
                    notation = String.format("%c%d", colT, rowT);
                } else {
                    notation = String.format("%cx%c%d", colS, colT, rowT);
                }
            }
        } else if (source instanceof Knight) {
            if (target == null) {
                if (isDisambiguation){
                    notation = String.format("N%c%c%d",colS,colT, rowT);
                }else{
                    notation = String.format("N%c%d", colT, rowT);
                }
            } else {
                if (isDisambiguation){
                    notation = String.format("N%cx%c%d",colS,colT, rowT);
                }else{
                    notation = String.format("Nx%c%d", colT, rowT);
                }
            }
        } else if (source instanceof Bishop) {
            if (target == null) {
                if (isDisambiguation){
                    notation = String.format("B%c%c%d",colS,colT, rowT);
                }else{
                    notation = String.format("B%c%d", colT, rowT);
                }
            } else {
                if (isDisambiguation){
                    notation = String.format("B%cx%c%d",colS,colT, rowT);
                }else{
                    notation = String.format("Bx%c%d", colT, rowT);
                }
            }
        } else if (source instanceof Rook) {
            if (target == null) {
                if (isDisambiguation){
                    notation = String.format("R%c%c%d",colS,colT, rowT);
                }else{
                    notation = String.format("R%c%d", colT, rowT);
                }
            } else {
                if (isDisambiguation){
                    notation = String.format("R%cx%c%d",colS,colT, rowT);
                }else{
                    notation = String.format("Rx%c%d", colT, rowT);
                }
            }
        } else if (source instanceof Queen) {
            if (target == null) {
                if (isDisambiguation){
                    notation = String.format("Q%c%c%d",colS,colT, rowT);
                }else{
                    notation = String.format("Q%c%d", colT, rowT);
                }
            } else {
                if (isDisambiguation){
                    notation = String.format("Q%cx%c%d",colS,colT, rowT);
                }else{
                    notation = String.format("Qx%c%d", colT, rowT);
                }
            }
        } else if (source instanceof King) {
            if (toPoint instanceof CastleMove){
                if (colT == 'g'){
                    notation = "O-O";
                }else {
                    notation = "O-O-O";
                }
            }else{
                if (target == null) {
                    notation = String.format("K%c%d", colT, rowT);
                } else {
                    notation = String.format("Kx%c%d", colT, rowT);
                }
            }
        }

        if (isKingCheck) {

            if (isCheckMate()) {
                //checkmate
                if (currentPlayer == ChessColor.BLACK) {
                    notation += " 1-0";
                    Game.isItEndGame = true;

                    Game.whoWins = ChessColor.WHITE;
                } else {
                    notation += " 0-1";
                    Game.isItEndGame = true;

                    Game.whoWins = ChessColor.BLACK;

                }
            } else {
                notation += "+";// only for check
            }
        }
        if (isCheckMate()&&!isKingCheck){
            notation += " 1/2-1/2";
            Game.isItEndGame = true;
        }
        //
        System.out.println(notation);
        return notation;
    }

    private Piece getCurrentKing() {
        for (Piece[] pieces1 : pieces) {
            for (Piece piece : pieces1) {
                if (piece instanceof King && piece.getChessColor() == getCurrentPlayer()) {
                    return piece;
                }
            }
        }
        return null;
    }

    public ChessboardPoint moveChess(ChessboardPoint from, ChessboardPoint to) {
        Piece source = getChess(from);
        Piece target = getChess(to);
        Boolean anyDisambiguation = Game.disambiguation(pieces,source,to);
        ChessboardPoint move = getMove(getCanMovePoints(from), to);
        if (move == null) // invalid move
            return null;

        enPassantTarget = null; // reset en passant

        // regular move
        source.setLocation(to);
        pieces[to.X][to.Y] = source;
        pieces[from.X][from.Y] = null;
        //record the notation
        /*   special moves   */
        if (move instanceof SpecialMove) {
            if (move instanceof PawnTwoStepMove) {

                enPassantTarget = (Pawn) source;

            } else if (move instanceof EnPassantMove) {
                EnPassantMove m = (EnPassantMove) move;
                pieces[m.capturePawn.X][m.capturePawn.Y] = null;
            } else if (move instanceof CastleMove) {
                pieces[move.X][move.Y == 2 ? 3 : 5] = pieces[move.X][move.Y == 2 ? 0 : 7];
                pieces[move.X][move.Y == 2 ? 0 : 7].setLocation(new ChessboardPoint(move.X, move.Y == 2 ? 3 : 5));
                pieces[move.X][move.Y == 2 ? 0 : 7] = null;
            } else if (move instanceof PromotionPawnMove) {
                pieces[to.X][to.Y] = new Queen(to, currentPlayer);
            }
        }

        this.currentPlayer = (this.currentPlayer == ChessColor.BLACK) ? ChessColor.WHITE : ChessColor.BLACK;
        chessNotation.add(getNotation(move, from, source, target, Utils.isKingCheck(pieces, getCurrentKing().getLocation(), getCurrentKing()),anyDisambiguation));

        return move;
    }

    public static boolean isCheckMate() {
        for (Piece[] components : pieces) {
            for (Piece component : components) {
                if (component != null && component.getChessColor() == getCurrentPlayer() && !getCanMovePoints(component.getLocation()).isEmpty()) {
                    return false;
                }
            }

        }
        return true;
    }

    private static ChessboardPoint getMove(List<ChessboardPoint> points, ChessboardPoint point) {
        for (ChessboardPoint p : points) {
            if (p.X == point.X && p.Y == point.Y)
                return p;
        }
        return null;
    }
    public ArrayList<String> getChessNotation(){ //call this method to get chess notation
        return chessNotation;
    }

}