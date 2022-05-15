package com.backend.piece;

import com.ChessColor;
import com.ChessboardPoint;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(ChessboardPoint location, ChessColor chessColor){
        super(location,
                chessColor,
                (chessColor == ChessColor.BLACK) ? 'K' : 'k');
    }
    private static final int[][] MOVES = {{1,0}, {-1,0}, {0,1}, {0,-1}, {1,1}, {-1,-1}, {1,-1}, {-1,1}};

    @Override
    ArrayList<ChessboardPoint> getCanMovePoints(Piece[][] board) {
        ArrayList<ChessboardPoint> lists = new ArrayList<>();

        for(int[] move : MOVES){
            utils.add(location, board, location.offset(move[0], move[1]), lists);
        }

        return lists;
    }
}
