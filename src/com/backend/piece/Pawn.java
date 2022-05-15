package com.backend.piece;

import com.ChessColor;
import com.ChessboardPoint;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(ChessboardPoint location, ChessColor chessColor){
        super(location,
                chessColor,
                (chessColor == ChessColor.BLACK) ? 'P' : 'p');
    }
//    private static final int[][] MOVES = {{2,1}, {2,-1}, {-2,1}, {-2,-1}, {1,-2}, {1,2}, {-1,-2}, {-1,2}};
private final static ChessboardPoint[] WHITE_PAWN_MOVES = {
        new ChessboardPoint(-1,0),
        new ChessboardPoint(-1,-1),
        new ChessboardPoint(-1,1),

};
    private final static ChessboardPoint[] BLACK_PAWN_MOVES = {
            new ChessboardPoint(1,0),
            new ChessboardPoint(1,-1),
            new ChessboardPoint(1,1),

    };
    @Override
    ArrayList<ChessboardPoint> getCanMovePoints(Piece[][] board) {
        final ChessboardPoint initialCoordinate = new ChessboardPoint(location.X, location.Y);
        ArrayList<ChessboardPoint> legalMoves =  new ArrayList<>();
        //I WILL FIX THIS PART LATER
//        if (this.getChessColor()==ChessColor.BLACK){
//            for (ChessboardPoint tile :
//                    BLACK_PAWN_MOVES) {
//                final ChessboardPoint candidatePoint = initialCoordinate.offset(tile.getX(), tile.getY());
//                if (candidatePoint!=null&&board[candidatePoint.getX()][candidatePoint.getY()].getChessColor()!=this.getChessColor()){
//                    if (board[candidatePoint.getX()][candidatePoint.getY()]!=null&&candidatePoint.getY()!= initialCoordinate.getY()) legalMoves.add(candidatePoint);
//
//                    if (board[candidatePoint.getX()][candidatePoint.getY()]!=null&&candidatePoint.getY()== initialCoordinate.getY()) {
//                        legalMoves.add(candidatePoint);
//                        if (board[candidatePoint.getX()+1][candidatePoint.getY()]!=null&&initialCoordinate.getX()==1) {
//                            legalMoves.add(new ChessboardPoint(candidatePoint.getX() + 1, candidatePoint.getY()));
//                        }
//                    }
//                }
//            }
//
//        }else{
//            for (ChessboardPoint tile :
//                    WHITE_PAWN_MOVES) {
//                final ChessboardPoint candidatePoint = initialCoordinate.offset(tile.getX(), tile.getY());
//                if (candidatePoint!=null&&board[candidatePoint.getX()][candidatePoint.getY()].getChessColor()!=this.getChessColor()){
//                    if (board[candidatePoint.getX()][candidatePoint.getY()]!=null&&candidatePoint.getY()!= initialCoordinate.getY()) legalMoves.add(candidatePoint);
//
//                    if (board[candidatePoint.getX()][candidatePoint.getY()]!=null&&candidatePoint.getY()== initialCoordinate.getY()) {
//                        legalMoves.add(candidatePoint);
//                        if (board[candidatePoint.getX()-1][candidatePoint.getY()]!=null&&initialCoordinate.getX()==6) {
//                            legalMoves.add(new ChessboardPoint(candidatePoint.getX() - 1, candidatePoint.getY()));
//                        }
//                    }
//                }
//            }
//        }
        return legalMoves;
    }
}
