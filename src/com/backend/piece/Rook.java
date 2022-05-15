package com.backend.piece;

import com.*;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(ChessboardPoint location, ChessColor chessColor){
        super(location,
                chessColor,
                (chessColor == ChessColor.BLACK) ? 'R' : 'r');
    }
    private static final int[][] MOVES = {{0,1}, {0,-1}, {1,0}, {-1,0}};

    @Override
    ArrayList<ChessboardPoint> getCanMovePoints(Piece[][] board) {
        ArrayList<ChessboardPoint> lists = new ArrayList<>();
        utils.scan(location, board, MOVES, lists);

        return lists;
    }
}
