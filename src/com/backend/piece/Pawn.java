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

    @Override
    ArrayList<ChessboardPoint> getCanMovePoints(Piece[][] board) {
        ArrayList<ChessboardPoint> lists = new ArrayList<>();

        /*
        need implementations
         */

        return lists;
    }
}
