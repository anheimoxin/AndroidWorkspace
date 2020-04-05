package com.anheimoxin.chinesechess.local.entity.pawn;

import com.anheimoxin.chinesechess.local.entity.pawn.base.Pawn;

public class Bing extends Pawn {
    public Bing(int color, int id, int camp, String name, int row, int col, boolean alive, boolean isSelected) {
        super(color, id, camp, name, row, col, alive, isSelected);
    }

    /**
     * 1.可以横着走
     * 2.可以竖着走
     * 3.可以过河
     * 4.过河才能横着走
     * 5.永远都不能回头
     */
    public static boolean canMove(Pawn[][] pawns, int rowFrom, int colFrom, int rowTo, int colTo, int moveTarget) {
        if (moveTarget == 10) {//上下走
            if (1 == pawns[rowFrom][colFrom].getCamp()) {//黑 只能向下走
                if (pawns[rowFrom][colFrom].getRow() < rowTo) {
                    return true;
                }
            } else if (0 == pawns[rowFrom][colFrom].getCamp()) {
                if (pawns[rowFrom][colFrom].getRow() > rowTo) {//红 只能向上走
                    return true;
                }
            }
        } else if (moveTarget == 1) {//左右走
            //过河才能左右走
            if (1 == pawns[rowFrom][colFrom].getCamp()) {//黑
                if (pawns[rowFrom][colFrom].getRow() >= 5) {
                    return true;
                }
            } else if (0 == pawns[rowFrom][colFrom].getCamp()) {//红
                if (pawns[rowFrom][colFrom].getRow() <= 4) {
                    return true;
                }
            }
        }

        return false;
    }
}
