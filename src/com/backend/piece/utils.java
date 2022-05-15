package com.backend.piece;

import com.ChessboardPoint;

import java.util.List;

public class utils {
    static void scan(ChessboardPoint source, Piece[][] board, int[][] directions, List storeLocation){
        for(int[] direction : directions){
            int dx = 0;
            int dy = 0;
            while(true){
                dx += direction[0];
                dy += direction[1];

                if(!utils.add(source, board, source.offset(dx, dy), storeLocation))
                    break;
            }
        }
    }
    static boolean add(ChessboardPoint source, Piece[][] board, ChessboardPoint point, List storeLocation){
        if(point == null) return false;

        if(board[point.X][point.Y] == null){
            storeLocation.add(point);
            return true;
        }

        if(board[point.X][point.Y].getChessColor() != board[source.X][source.Y].getChessColor())
            storeLocation.add(point);

        return false;
    }
}
