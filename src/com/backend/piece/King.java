package com.backend.piece;

import com.ChessColor;
import com.ChessboardPoint;

import java.util.ArrayList;

public class King extends Piece {
    private boolean hasMoved;
    private final int[] direction = {2, 6};

    public King(ChessboardPoint location, ChessColor chessColor) {
        super(location,
                chessColor,
                (chessColor == ChessColor.BLACK) ? 'K' : 'k');

        hasMoved = false;
    }

    boolean getHasMoved() {
        return hasMoved;
    }

    private static final int[][] MOVES = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

    @Override
    ArrayList<ChessboardPoint> getCanMovePoints(Piece[][] board) {
        ArrayList<ChessboardPoint> lists = new ArrayList<>();

        for (int[] move : MOVES) {
            Utils.add(location, board, location.offset(move[0], move[1]), lists);
        }
        if (!hasMoved) {
            for (int castlingTarget :
                    direction) {
                if (board[location.X][castlingTarget] == null) {
                    lists.add(new ChessboardPoint(location.X,castlingTarget));
                }
            }
        }
        return lists;
    }

    @Override
    public void setLocation(ChessboardPoint location) {
        hasMoved = true;
        super.setLocation(location);
    }
}
