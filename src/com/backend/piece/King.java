package com.backend.piece;

import com.ChessColor;
import com.ChessboardPoint;
import com.backend.special_moves.CastleMove;

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
                if (board[location.X][castlingTarget] == null&&
                        board[location.X][castlingTarget-1]==null&&
                        board[location.X][castlingTarget==2?castlingTarget+1:castlingTarget-1]==null&&
                        !Utils.isKingCheck(board,new ChessboardPoint(location.X,castlingTarget==2?castlingTarget+1:castlingTarget-1),this)&&
                        board[location.X][castlingTarget==2?0:7] instanceof Rook &&
                        !((Rook) board[location.X][castlingTarget==2?0:7]).getHasMoved()) {
                    lists.add(new CastleMove(location.X,castlingTarget));
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
