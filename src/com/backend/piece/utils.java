package com.backend.piece;

import com.ChessboardPoint;

import java.util.List;

public class utils {
    static void scan(ChessboardPoint source, Piece[][] board, int[][] directions, List storeLocation){
        for(int[] direction : directions){
            int dx = 0;
            int dy = 0;
            do {
                dx += direction[0];
                dy += direction[1];

            } while (utils.add(source, board, source.offset(dx, dy), storeLocation));
        }

    }
    private static  final int[][] KING_CHECK_MOVES = {{1,0}, {-1,0}, {0,1}, {0,-1}, {1,1}, {-1,-1}, {1,-1}, {-1,1}};
    public static boolean isKingCheck(final Piece[][] board,final ChessboardPoint piecePosition, final Piece selectedPiece){
        Piece[][] testBoard = new Piece[8][8];
        for(int i=0; i<board.length; i++)
            for(int j=0; j<board[i].length; j++)
                testBoard[i][j]=board[i][j];
        testBoard[piecePosition.X][piecePosition.Y] = selectedPiece;
        testBoard[selectedPiece.location.X][selectedPiece.location.Y]=null;
         ChessboardPoint kingPosition = null;
         int a = 0;
        outer:for (Piece[] row:
             testBoard) {
            for (Piece piece:
                 row) {

                if (piece instanceof King && piece.chessColor==selectedPiece.chessColor){
                    kingPosition = piece.location;
                    break outer;
                }
            }
        }
        for (int[] candidateCoordinate:
             KING_CHECK_MOVES) {
            ChessboardPoint candidatePoint = new ChessboardPoint(kingPosition.X, kingPosition.Y).offset(candidateCoordinate[0],candidateCoordinate[1]);

            while (candidatePoint!=null){

                if(testBoard[candidatePoint.X][candidatePoint.Y] != null){
                    if (testBoard[candidatePoint.X][candidatePoint.Y].getChessColor()!= selectedPiece.chessColor){
                        return  true;
                    }else{
                        break;
                    }
                }


                candidatePoint = candidatePoint.offset(candidateCoordinate[0], candidateCoordinate[1]);
            }
        }
        return  false;
    }

    static boolean add(ChessboardPoint source, Piece[][] board, ChessboardPoint point, List storeLocation){
        if(point == null) return false;

        if(board[point.X][point.Y] == null){
            storeLocation.add(point);
            return true;
        }

        if(board[point.X][point.Y].getChessColor() != board[source.X][source.Y].getChessColor())
            storeLocation.add(point);

        return false;
    }
}
