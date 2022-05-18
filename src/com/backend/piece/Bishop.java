package com.backend.piece;

import com.ChessColor;
import com.ChessboardPoint;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(ChessboardPoint location, ChessColor chessColor){
        super(location,
                chessColor,
                (chessColor == ChessColor.BLACK) ? 'B' : 'b');
    }
    private static final int[][] MOVES = {{1,1}, {-1,-1}, {1,-1}, {-1,1}};

    @Override
    ArrayList<ChessboardPoint> getCanMovePoints(Piece[][] board) {
        ArrayList<ChessboardPoint> lists = new ArrayList<>();
        Utils.scan(location, board, MOVES, lists);

        return lists;
    }
}
