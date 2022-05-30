package com.backend.piece;

import com.ChessColor;
import com.ChessboardPoint;
import com.backend.special_moves.PawnTwoStepMove;
import com.backend.special_moves.PromotionPawnMove;

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
            if (point.X==7||point.X==0){
                points.add(new PromotionPawnMove(point.X,point.Y));
            }else {
                points.add(point);
            }

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
}
