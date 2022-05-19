package com.backend;

import com.ChessColor;
import com.ChessboardPoint;
import com.backend.piece.*;
import com.backend.special_moves.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    // A 2-dimension array to store all the chess components
    // should be initialized in your construct method.
    // i.e. = new com.backend.ChessComponent[8][8]
    private final Piece[][] pieces;

    // What's the current player's color, black or white?
    // should be initialized in your construct method.
    // by default, set the color to white.
    private ChessColor currentPlayer;
    private Pawn enPassantTarget;

    public Game() {
        pieces = new Piece[8][8];
        Piece.setChessBoard(this.pieces);
    }

    public Piece[][] getChessComponents() {
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

    public ChessColor getCurrentPlayer() {
        return this.currentPlayer;
    }
    public ChessColor getOpponentPlayer(){
        if (getCurrentPlayer()==ChessColor.BLACK) return ChessColor.WHITE;
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

    public Piece getChess(ChessboardPoint source) {
        return pieces[source.X][source.Y];
    }

    public List<ChessboardPoint> getCanMovePoints(ChessboardPoint source) {
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

    public ChessboardPoint moveChess(ChessboardPoint from, ChessboardPoint to) {
        Piece source = getChess(from);
        Piece target = getChess(to);

        ChessboardPoint move = getMove(getCanMovePoints(from), to);
        if (move == null) // invalid move
            return null;

        enPassantTarget = null; // reset en passant

        // regular move
        source.setLocation(to);
        pieces[to.X][to.Y] = source;
        pieces[from.X][from.Y] = null;

        /*   special moves   */
        if (move instanceof SpecialMove) {
            if (move instanceof PawnTwoStepMove) {

                enPassantTarget = (Pawn) source;

            } else if (move instanceof EnPassantMove) {
                EnPassantMove m = (EnPassantMove) move;
                pieces[m.capturePawn.X][m.capturePawn.Y] = null;
            }else if (move instanceof CastleMove) {
                pieces[move.X][move.Y == 2 ? 3 : 5] = pieces[move.X][move.Y == 2 ? 0 : 7];
                pieces[move.X][move.Y == 2 ? 0 : 7].setLocation(new ChessboardPoint(move.X, move.Y == 2 ? 3 : 5));
                pieces[move.X][move.Y == 2 ? 0 : 7] = null;
            }else if(move instanceof PromotionPawnMove){
                pieces[to.X][to.Y] = new Queen(to,currentPlayer);
            }
        }


        this.currentPlayer = (this.currentPlayer == ChessColor.BLACK) ? ChessColor.WHITE : ChessColor.BLACK;

        return move;
    }

    public boolean isCheckMate() {
        for (Piece[] components : pieces) {
            for (Piece component : components) {
                if (component!=null&&component.getChessColor() == getCurrentPlayer() && getCanMovePoints(component.getLocation()).isEmpty()) {
                    return true;
                }
            }

        }
        return false;
    }

    private ChessboardPoint getMove(List<ChessboardPoint> points, ChessboardPoint point) {
        for (ChessboardPoint p : points) {
            if (p.X == point.X && p.Y == point.Y)
                return p;
        }
        return null;
    }
}