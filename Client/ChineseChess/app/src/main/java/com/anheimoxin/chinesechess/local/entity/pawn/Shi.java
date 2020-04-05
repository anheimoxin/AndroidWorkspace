package com.anheimoxin.chinesechess.local.entity.pawn;

import com.anheimoxin.chinesechess.local.entity.pawn.base.Pawn;

public class Shi extends Pawn {
    public Shi(int color, int id, int camp, String name, int row, int col, boolean alive, boolean isSelected) {
        super(color, id, camp, name, row, col, alive, isSelected);
    }

    /**
     * 1.走斜线
     * 2.不能出 “田” 字
     */
    public static boolean canMove(Pawn[][] pawns, int rowFrom, int colFrom, int rowTo, int colTo, int moveTarget) {
        if (moveTarget == 11) {
            if (colTo >= 3 && colTo <= 5) {
                if (rowFrom <= 2) {
                    if (rowTo >= 0 && rowTo <= 2) {
                        return true;
                    }
                } else if (rowFrom >= 7) {
                    if (rowTo >= 7 && rowTo <= 9) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
