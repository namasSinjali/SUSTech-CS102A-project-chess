package com.backend.special_moves;

import com.ChessboardPoint;
import com.backend.piece.Pawn;

public class EnPassantMove extends ChessboardPoint implements SpecialMove {
    public final ChessboardPoint capturePawn;
    public EnPassantMove(ChessboardPoint target, ChessboardPoint capturePawn){
        super(target.X, target.Y);
        this.capturePawn = capturePawn;
    }
}
