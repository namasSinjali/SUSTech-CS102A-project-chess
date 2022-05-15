package com.backend.piece;

import com.ChessColor;
import com.ChessboardPoint;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    ChessboardPoint location;
    final ChessColor chessColor;
    final char name;

    // constructor needed because some fields are final
    public Piece(ChessboardPoint location, ChessColor chessColor, char name) {
        this.location = location;
        this.chessColor = chessColor;
        this.name = name;
    };

    // this stores references to board where pieces are stored
    private static Piece[][] chessBoard;
    public static void setChessBoard(Piece[][] board){
        chessBoard = board;
    }

    public ChessboardPoint getLocation() {
        return location;
    }
    public void setLocation(ChessboardPoint location){
        this.location = location;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

    public ArrayList<ChessboardPoint> getCanMovePoints(){ //this is exposed to public
        return getCanMovePoints(chessBoard);
    };
    abstract ArrayList<ChessboardPoint> getCanMovePoints(Piece[][] board); // this calculates all the moves

}
