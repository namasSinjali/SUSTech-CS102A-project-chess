package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {
    final Board board;
    final Piece movedPieced;
    final int destinationCoordinate;

    private Move(final Board board,final Piece movedPieced,final int destinationCoordinate) {
        this.board = board;
        this.movedPieced = movedPieced;
        this.destinationCoordinate = destinationCoordinate;
    }

    public static final class MajorMove extends Move{

        public MajorMove(final Board board, final Piece movedPieced, final int destinationCoordinate) {
            super(board, movedPieced, destinationCoordinate);
        }
    }
    public static final class AttackMove extends Move{
        final  Piece attackedPiece;
        public AttackMove(final Board board, final Piece movedPieced, final int destinationCoordinate,final Piece attackedPiece) {
            super(board, movedPieced, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }
}
