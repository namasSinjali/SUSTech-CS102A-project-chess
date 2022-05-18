package com.backend.special_moves;

import com.ChessboardPoint;

public class PawnTwoStepMove extends ChessboardPoint implements SpecialMove {
    public PawnTwoStepMove(ChessboardPoint target){
        super(target.X, target.Y);
    }
}
