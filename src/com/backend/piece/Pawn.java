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

        if (this.chessColor==ChessColor.BLACK){
            for (int[] tile :
                    BLACK_PAWN_MOVES) {
               final ChessboardPoint candidatePoint = initialCoordinate.offset(tile[0], tile[1]);

                if (candidatePoint!=null){

                    if (Utils.isChanceOfPawnSpecialMove){
                        for (ChessboardPoint coordinateWhoCanSpecialMove :
                                Utils.whoCanDoPawnSpecialMove) {
                            if (coordinateWhoCanSpecialMove!=null){
                                int dx = coordinateWhoCanSpecialMove.X;
                                int dy = coordinateWhoCanSpecialMove.Y;
                                if (this.location.X == dx && this.location.Y == dy){
                                    legalMoves.add(Utils.candidateTargetCoordinate);
                                }
                            }
                        }
                    }

                    if (board[candidatePoint.getX()][candidatePoint.getY()]==null&&candidatePoint.getY()== initialCoordinate.getY()) {
                        legalMoves.add(candidatePoint);

                        if (board[candidatePoint.getX()+1][candidatePoint.getY()]==null&&initialCoordinate.getX()==1) {
                            legalMoves.add(new ChessboardPoint(candidatePoint.getX() + 1, candidatePoint.getY()));

                            if ((candidatePoint.Y+1<8&&board[candidatePoint.getX()+1][candidatePoint.getY()+1]!=null&&board[candidatePoint.getX()+1][candidatePoint.getY()+1].chessColor != chessColor)||
                                    candidatePoint.Y-1>=0&&board[candidatePoint.getX()+1][candidatePoint.getY()-1]!=null && board[candidatePoint.getX()+1][candidatePoint.getY()-1].chessColor!= chessColor){
                                Utils.setWhoCanDoPawnSpecialMove(new ChessboardPoint(candidatePoint.getX()+1,candidatePoint.getY()).offset(0,-1),new ChessboardPoint(candidatePoint.getX()+1,candidatePoint.getY()).offset(0,1),candidatePoint,new ChessboardPoint(candidatePoint.getX()+1,candidatePoint.getY()));
                            }

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
                    if (Utils.isChanceOfPawnSpecialMove){
                    for (ChessboardPoint coordinateWhoCanSpecialMove :
                            Utils.whoCanDoPawnSpecialMove) {
                        if (coordinateWhoCanSpecialMove!=null){
                            int dx = coordinateWhoCanSpecialMove.X;
                            int dy = coordinateWhoCanSpecialMove.Y;
                            if (this.location.X == dx && this.location.Y == dy){
                                legalMoves.add(Utils.candidateTargetCoordinate);
                                }
                            }
                        }
                    }

                    if (board[candidatePoint.getX()][candidatePoint.getY()]==null&&candidatePoint.getY()== initialCoordinate.getY()) {
                        legalMoves.add(candidatePoint);
                        if (board[candidatePoint.getX()-1][candidatePoint.getY()]==null&&initialCoordinate.getX()==6) {
                            legalMoves.add(new ChessboardPoint(candidatePoint.getX() - 1, candidatePoint.getY()));

                            if (candidatePoint.Y+1<8&&(board[candidatePoint.getX()-1][candidatePoint.getY()+1]!=null&&board[candidatePoint.getX()-1][candidatePoint.getY()+1].chessColor != chessColor)||
                                    candidatePoint.Y-1>=0&&board[candidatePoint.getX()-1][candidatePoint.getY()-1]!=null && board[candidatePoint.getX()-1][candidatePoint.getY()-1].chessColor!= chessColor){
                                Utils.setWhoCanDoPawnSpecialMove(new ChessboardPoint(candidatePoint.getX()-1,candidatePoint.getY()).offset(0,-1),new ChessboardPoint(candidatePoint.getX()-1,candidatePoint.getY()).offset(0,1),candidatePoint,new ChessboardPoint(candidatePoint.getX()-1,candidatePoint.getY()));
                            }
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
