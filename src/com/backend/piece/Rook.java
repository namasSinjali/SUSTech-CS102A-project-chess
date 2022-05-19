package com.backend.piece;

import com.*;

import java.util.ArrayList;

public class Rook extends Piece {
    private boolean hasMoved;
    public Rook(ChessboardPoint location, ChessColor chessColor){
        super(location,
                chessColor,
                (chessColor == ChessColor.BLACK) ? 'R' : 'r');
        hasMoved = false;

    }

    @Override
    public void setLocation(ChessboardPoint location) {
        hasMoved = true;
        super.setLocation(location);
    }

    private static final int[][] MOVES = {{0,1}, {0,-1}, {1,0}, {-1,0}};

    @Override
    ArrayList<ChessboardPoint> getCanMovePoints(Piece[][] board) {
        ArrayList<ChessboardPoint> lists = new ArrayList<>();
        Utils.scan(location, board, MOVES, lists);

        return lists;
    }
}
