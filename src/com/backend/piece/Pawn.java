package com.backend.piece;

import com.ChessColor;
import com.ChessboardPoint;
import com.backend.special_moves.EnPassantMove;
import com.backend.special_moves.PawnTwoStepMove;

import java.util.ArrayList;

public class Pawn extends Piece {
    public final int direction;
    public Pawn(ChessboardPoint location, ChessColor chessColor){
        super(location,
                chessColor,
                (chessColor == ChessColor.BLACK) ? 'P' : 'p');
        direction = chessColor == ChessColor.BLACK ? 1 : -1;
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
        ArrayList<ChessboardPoint> points = new ArrayList<>();
        ChessboardPoint point;

        point = this.location.offset(direction, 0);
        if(point != null && board[point.X][point.Y] == null){
            points.add(point);

            if(this.location.X == direction || this.location.X == direction + 7){
                point = this.location.offset(direction+direction, 0);
                if(point != null && board[point.X][point.Y] == null)
                    points.add(new PawnTwoStepMove(point));
            }
        }

        //attacking moves
        for(int i : new int[]{-1, 1}){
            point = this.location.offset(direction, i);
            if(point != null &&
                    board[point.X][point.Y] != null &&
                    board[point.X][point.Y].getChessColor() != this.chessColor
            ){
                points.add(point);
            }
        }

        //en passant move will be added by Game getCanMovePoints() method class

        return points;
    }
//    @Override
//    ArrayList<ChessboardPoint> getCanMovePoints(Piece[][] board) {
//        final ChessboardPoint initialCoordinate = new ChessboardPoint(location.X, location.Y);
//        ArrayList<ChessboardPoint> legalMoves =  new ArrayList<>();
//
//        if (this.chessColor==ChessColor.BLACK){
//            for (int[] tile :
//                    BLACK_PAWN_MOVES) {
//               final ChessboardPoint candidatePoint = initialCoordinate.offset(tile[0], tile[1]);
//
//                if (candidatePoint!=null){
//
//                    if (Utils.isChanceOfPawnSpecialMove){
//                        for (ChessboardPoint coordinateWhoCanSpecialMove :
//                                Utils.whoCanDoPawnSpecialMove) {
//                            if (coordinateWhoCanSpecialMove!=null){
//                                int dx = coordinateWhoCanSpecialMove.X;
//                                int dy = coordinateWhoCanSpecialMove.Y;
//                                if (this.location.X == dx && this.location.Y == dy){
////                                    legalMoves.add(Utils.candidateTargetCoordinate);
//                                    legalMoves.add(new EnPassantMove(Utils.candidateTargetCoordinate, Utils.candidateWillDeathPawn));
//                                }
//                            }
//                        }
//                    }
//
//                    if (board[candidatePoint.getX()][candidatePoint.getY()]==null&&candidatePoint.getY()== initialCoordinate.getY()) {
//                        legalMoves.add(candidatePoint);
//
//                        if (board[candidatePoint.getX()+1][candidatePoint.getY()]==null&&initialCoordinate.getX()==1) {
//                            legalMoves.add(new ChessboardPoint(candidatePoint.getX() + 1, candidatePoint.getY()));
//
//                            if ((candidatePoint.Y+1<8&&board[candidatePoint.getX()+1][candidatePoint.getY()+1]!=null&&board[candidatePoint.getX()+1][candidatePoint.getY()+1].chessColor != chessColor)||
//                                    candidatePoint.Y-1>=0&&board[candidatePoint.getX()+1][candidatePoint.getY()-1]!=null && board[candidatePoint.getX()+1][candidatePoint.getY()-1].chessColor!= chessColor){
//                                Utils.setWhoCanDoPawnSpecialMove(new ChessboardPoint(candidatePoint.getX()+1,candidatePoint.getY()).offset(0,-1),new ChessboardPoint(candidatePoint.getX()+1,candidatePoint.getY()).offset(0,1),candidatePoint,new ChessboardPoint(candidatePoint.getX()+1,candidatePoint.getY()));
//                            }
//
//                        }
//                    }
//
//                    if (board[candidatePoint.getX()][candidatePoint.getY()]!=null&&candidatePoint.getY()!= initialCoordinate.getY()&&board[candidatePoint.getX()][candidatePoint.getY()].chessColor != this.chessColor) {
//                        legalMoves.add(candidatePoint);
//
//                    }
//                }
//            }
//
//        }else{
//
//            for (int[] tile :
//                    WHITE_PAWN_MOVES) {
//                final ChessboardPoint candidatePoint = initialCoordinate.offset(tile[0], tile[1]);
//                if (candidatePoint!=null){
//                    if (Utils.isChanceOfPawnSpecialMove){
//                    for (ChessboardPoint coordinateWhoCanSpecialMove :
//                            Utils.whoCanDoPawnSpecialMove) {
//                        if (coordinateWhoCanSpecialMove!=null){
//                            int dx = coordinateWhoCanSpecialMove.X;
//                            int dy = coordinateWhoCanSpecialMove.Y;
//                            if (this.location.X == dx && this.location.Y == dy){
//                                legalMoves.add(Utils.candidateTargetCoordinate);
//                                }
//                            }
//                        }
//                    }
//
//                    if (board[candidatePoint.getX()][candidatePoint.getY()]==null&&candidatePoint.getY()== initialCoordinate.getY()) {
//                        legalMoves.add(candidatePoint);
//                        if (board[candidatePoint.getX()-1][candidatePoint.getY()]==null&&initialCoordinate.getX()==6) {
//                            legalMoves.add(new ChessboardPoint(candidatePoint.getX() - 1, candidatePoint.getY()));
//
//                            if (candidatePoint.Y+1<8&&(board[candidatePoint.getX()-1][candidatePoint.getY()+1]!=null&&board[candidatePoint.getX()-1][candidatePoint.getY()+1].chessColor != chessColor)||
//                                    candidatePoint.Y-1>=0&&board[candidatePoint.getX()-1][candidatePoint.getY()-1]!=null && board[candidatePoint.getX()-1][candidatePoint.getY()-1].chessColor!= chessColor){
//                                Utils.setWhoCanDoPawnSpecialMove(new ChessboardPoint(candidatePoint.getX()-1,candidatePoint.getY()).offset(0,-1),new ChessboardPoint(candidatePoint.getX()-1,candidatePoint.getY()).offset(0,1),candidatePoint,new ChessboardPoint(candidatePoint.getX()-1,candidatePoint.getY()));
//                            }
//                        }
//                    }
//
//                    if (board[candidatePoint.getX()][candidatePoint.getY()]!=null&&candidatePoint.getY()!= initialCoordinate.getY()&&board[candidatePoint.getX()][candidatePoint.getY()].chessColor != this.chessColor) {
//                        legalMoves.add(candidatePoint);
//
//                    }
//                }
//            }
//        }
//        return legalMoves;
//    }
}
