package com;

public class ChessboardPoint {
    public final int X;
    public final int Y;

    /**
     * should design
     * @param x
     * @param y
     */
    public ChessboardPoint(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    /**
     * should design
     * @return
     */
    public int getX() {
        return X;
    }

    /**
     * should design
     * @return
     */
    public int getY() {
        return Y;
    }

    /**
     * should design
     * @return
     */
    @Override
    public String toString() {
        return String.format("(%d,%d)", X, Y);
    }


    /**
     * should design
     *
     * @param dx
     * @param dy
     * @return
     */
    public ChessboardPoint offset(int dx, int dy) {
        int x = this.X + dx;
        int y = this.Y + dy;
        if(ChessboardPoint.isValidPoint(x, y))
            return new ChessboardPoint(x, y);
        else
            return null;
    }
    public static boolean isValidPoint(int x, int y){
        return x>=0&&x<8&&y>=0&&y<8;
    }
}
