package com.backend.piece;

import com.ChessColor;
import com.ChessboardPoint;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public Queen(ChessboardPoint location, ChessColor chessColor){
        super(location,
                chessColor,
                (chessColor == ChessColor.BLACK) ? 'Q' : 'q');
    }
    public static final int[][] QUEEN_MOVES = {{1,0}, {-1,0}, {0,1}, {0,-1}, {1,1}, {-1,-1}, {1,-1}, {-1,1}};

    @Override
    ArrayList<ChessboardPoint> getCanMovePoints(Piece[][] board) {
        ArrayList<ChessboardPoint> lists = new ArrayList<>();
        utils.scan(location, board, QUEEN_MOVES, lists);

        return lists;
    }
}
