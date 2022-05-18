package com.backend.piece;

import com.ChessColor;
import com.ChessboardPoint;

import java.util.List;

public class Utils {
    static void scan(ChessboardPoint source, Piece[][] board, int[][] directions, List storeLocation) {
        for (int[] direction : directions) {
            int dx = 0;
            int dy = 0;
            do {
                dx += direction[0];
                dy += direction[1];

            } while (Utils.add(source, board, source.offset(dx, dy), storeLocation));
        }

    }

    public static boolean isChanceOfPawnSpecialMove = false;
    static ChessboardPoint[] whoCanDoPawnSpecialMove = new ChessboardPoint[2];
    static ChessboardPoint candidateTargetCoordinate = null;
    public static ChessboardPoint candidateWillDeathPawn = null;
    public static void setWhoCanDoPawnSpecialMove(ChessboardPoint first,
                                           ChessboardPoint second,
                                           ChessboardPoint candidateTargetCoordinateP,
                                           ChessboardPoint candidateWillDeath) {
        whoCanDoPawnSpecialMove[0] = first;
        whoCanDoPawnSpecialMove[1] = second;
        candidateTargetCoordinate = candidateTargetCoordinateP;
        isChanceOfPawnSpecialMove = true;
        candidateWillDeathPawn = candidateWillDeath;
    }
    public static void setWhoCanDoPawnSpecialMoveDefault() {
        whoCanDoPawnSpecialMove = new ChessboardPoint[2];
        isChanceOfPawnSpecialMove = false;
        candidateTargetCoordinate= null;
        candidateWillDeathPawn = null;
    }

    private static final int[][] KING_CHECK_MOVES = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
    private static final int[][] BLACK_PAWN_CHECK_MOVES = {{1, 1}, {1, -1}};
    private static final int[][] WHITE_PAWN_CHECK_MOVES = {{-1, 1}, {-1, -1}};
    private static final int[][] SPECIAL_KNIGHT_MOVES = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, -2}, {1, 2}, {-1, -2}, {-1, 2}};

    public static boolean isKingCheck(final Piece[][] board, final ChessboardPoint targetedPosition, final Piece selectedPiece) {
        Piece[][] testBoard = new Piece[8][8];
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                testBoard[i][j] = board[i][j];
        ChessboardPoint kingPosition = null;
        if (selectedPiece instanceof King) {
            testBoard[targetedPosition.X][targetedPosition.Y] = selectedPiece;
            testBoard[selectedPiece.location.X][selectedPiece.location.Y] = null;
            kingPosition = targetedPosition;
        } else {
            testBoard[targetedPosition.X][targetedPosition.Y] = selectedPiece;
            testBoard[selectedPiece.location.X][selectedPiece.location.Y] = null;

            outer:
            for (Piece[] row :
                    testBoard) {
                for (Piece piece :
                        row) {

                    if (piece instanceof King && piece.chessColor == selectedPiece.chessColor) {
                        kingPosition = piece.location;
                        break outer;
                    }
                }
            }
        }

        if (selectedPiece.chessColor == ChessColor.BLACK) {
            for (int[] candidateCoordinate :
                    BLACK_PAWN_CHECK_MOVES) {
                ChessboardPoint candidatePoint = new ChessboardPoint(kingPosition.X, kingPosition.Y).offset(candidateCoordinate[0], candidateCoordinate[1]);

                if (candidatePoint != null && testBoard[candidatePoint.X][candidatePoint.Y] != null &&
                        testBoard[candidatePoint.X][candidatePoint.Y].getChessColor() != selectedPiece.chessColor &&
                        testBoard[candidatePoint.X][candidatePoint.Y] instanceof Pawn) return true;
            }
        } else {
            for (int[] candidateCoordinate :
                    WHITE_PAWN_CHECK_MOVES) {
                ChessboardPoint candidatePoint = new ChessboardPoint(kingPosition.X, kingPosition.Y).offset(candidateCoordinate[0], candidateCoordinate[1]);

                if (candidatePoint != null && testBoard[candidatePoint.X][candidatePoint.Y] != null &&
                        testBoard[candidatePoint.X][candidatePoint.Y].getChessColor() != selectedPiece.chessColor &&
                        testBoard[candidatePoint.X][candidatePoint.Y] instanceof Pawn) return true;
            }
        }
        for (int[] candidateCoordinate :
                SPECIAL_KNIGHT_MOVES) {
            ChessboardPoint candidatePoint = new ChessboardPoint(kingPosition.X, kingPosition.Y).offset(candidateCoordinate[0], candidateCoordinate[1]);

            if (candidatePoint != null && testBoard[candidatePoint.X][candidatePoint.Y] != null &&
                    testBoard[candidatePoint.X][candidatePoint.Y].getChessColor() != selectedPiece.chessColor &&
                    testBoard[candidatePoint.X][candidatePoint.Y] instanceof Knight) return true;
        }
        for (int[] candidateCoordinate :
                KING_CHECK_MOVES) {

            ChessboardPoint candidatePoint = new ChessboardPoint(kingPosition.X, kingPosition.Y).offset(candidateCoordinate[0], candidateCoordinate[1]);
            while (candidatePoint != null) {

                if (testBoard[candidatePoint.X][candidatePoint.Y] != null) {
                    if (testBoard[candidatePoint.X][candidatePoint.Y].getChessColor() != selectedPiece.chessColor && !(testBoard[candidatePoint.X][candidatePoint.Y] instanceof Knight) && !(testBoard[candidatePoint.X][candidatePoint.Y] instanceof Pawn)) {

                        if ((testBoard[candidatePoint.X][candidatePoint.Y] instanceof Bishop || testBoard[candidatePoint.X][candidatePoint.Y] instanceof Queen) && //{{1,1}, {-1,-1}, {1,-1}, {-1,1}};
                                ((candidateCoordinate[0] == 1 && candidateCoordinate[1] == 1) ||
                                        (candidateCoordinate[0] == -1 && candidateCoordinate[1] == -1) ||
                                        (candidateCoordinate[0] == 1 && candidateCoordinate[1] == -1) ||
                                        (candidateCoordinate[0] == -1 && candidateCoordinate[1] == 1))) {
                            return true;
                        }
                        if ((testBoard[candidatePoint.X][candidatePoint.Y] instanceof Rook || testBoard[candidatePoint.X][candidatePoint.Y] instanceof Queen) &&
                                ((candidateCoordinate[0] == 0 && candidateCoordinate[1] == 1) ||
                                        (candidateCoordinate[0] == 0 && candidateCoordinate[1] == -1) ||
                                        (candidateCoordinate[0] == 1 && candidateCoordinate[1] == 0) ||
                                        (candidateCoordinate[0] == -1 && candidateCoordinate[1] == 0))) {
                            return true;
                        }


                    } else {
                        break;
                    }
                }


                candidatePoint = candidatePoint.offset(candidateCoordinate[0], candidateCoordinate[1]);
            }
        }
        return false;
    }

    static boolean add(ChessboardPoint source, Piece[][] board, ChessboardPoint point, List storeLocation) {
        if (point == null) return false;

        if (board[point.X][point.Y] == null) {
            storeLocation.add(point);
            return true;
        }

        if (board[point.X][point.Y].getChessColor() != board[source.X][source.Y].getChessColor())
            storeLocation.add(point);

        return false;
    }
}
