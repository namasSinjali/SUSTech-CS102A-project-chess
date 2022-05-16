package com.backend.piece;

import com.ChessColor;
import com.ChessboardPoint;

import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(ChessboardPoint location, ChessColor chessColor){
        super(location,
                chessColor,
                (chessColor == ChessColor.BLACK) ? 'P' : 'p');
    }
//    private static final int[][] MOVES = {{2,1}, {2,-1}, {-2,1}, {-2,-1}, {1,-2}, {1,2}, {-1,-2}, {-1,2}};
private final static int[][] WHITE_PAWN_MOVES = {
        {-1,0},
        {-1,-1},
        {-1,1},

};
    private final static int[][] BLACK_PAWN_MOVES = {
            {1,0},
            {1,-1},
            {1,1}

    };
    @Override
    ArrayList<ChessboardPoint> getCanMovePoints(Piece[][] board) {
        final ChessboardPoint initialCoordinate = new ChessboardPoint(location.X, location.Y);
        ArrayList<ChessboardPoint> legalMoves =  new ArrayList<>();
        //I WILL FIX THIS PART LATER

        if (this.chessColor==ChessColor.BLACK){
            for (int[] tile :
                    BLACK_PAWN_MOVES) {
               final ChessboardPoint candidatePoint = initialCoordinate.offset(tile[0], tile[1]);

                if (candidatePoint!=null){
                    if (board[candidatePoint.getX()][candidatePoint.getY()]==null&&candidatePoint.getY()== initialCoordinate.getY()) {
                        legalMoves.add(candidatePoint);
                        if (board[candidatePoint.getX()+1][candidatePoint.getY()]==null&&initialCoordinate.getX()==1) {
                            legalMoves.add(new ChessboardPoint(candidatePoint.getX() + 1, candidatePoint.getY()));
                        }
                    }

                    if (board[candidatePoint.getX()][candidatePoint.getY()]!=null&&candidatePoint.getY()!= initialCoordinate.getY()&&board[candidatePoint.getX()][candidatePoint.getY()].chessColor != this.chessColor) {
                        legalMoves.add(candidatePoint);

                    }
                }
            }

        }else{

            for (int[] tile :
                    WHITE_PAWN_MOVES) {
                final ChessboardPoint candidatePoint = initialCoordinate.offset(tile[0], tile[1]);
                if (candidatePoint!=null){
                    if (board[candidatePoint.getX()][candidatePoint.getY()]==null&&candidatePoint.getY()== initialCoordinate.getY()) {
                        legalMoves.add(candidatePoint);
                        if (board[candidatePoint.getX()-1][candidatePoint.getY()]==null&&initialCoordinate.getX()==6) {
                            legalMoves.add(new ChessboardPoint(candidatePoint.getX() - 1, candidatePoint.getY()));
                        }
                    }

                    if (board[candidatePoint.getX()][candidatePoint.getY()]!=null&&candidatePoint.getY()!= initialCoordinate.getY()&&board[candidatePoint.getX()][candidatePoint.getY()].chessColor != this.chessColor) {
                        legalMoves.add(candidatePoint);

                    }
                }
            }
        }
        return legalMoves;
    }
}
