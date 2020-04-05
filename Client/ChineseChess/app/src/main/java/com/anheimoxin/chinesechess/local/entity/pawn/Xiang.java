package com.anheimoxin.chinesechess.local.entity.pawn;

import com.anheimoxin.chinesechess.local.entity.pawn.base.Pawn;

public class Xiang extends Pawn {
    public Xiang(int color, int id, int camp, String name, int row, int col, boolean alive, boolean isSelected) {
        super(color, id, camp, name, row, col, alive, isSelected);
    }

    /**
     * 1.走“田”
     * 2.不能过河
     * 3.会被塞象脚
     */
    public static boolean canMove(Pawn[][] pawns, int rowFrom, int colFrom, int rowTo, int colTo, int moveTarget) {
        //1
        if (moveTarget == 22) {
            //2
            if ((rowFrom <= 4 && rowTo <= 4) || (rowFrom >= 5 && rowTo >= 5)) {
                //3
                Pawn p = pawns[(rowFrom + rowTo) / 2][(colFrom + colTo) / 2];
                if (p == null) {
                    return true;
                } else if (!p.isAlive()) {
                    return true;
                }
            }
        }
        return false;
    }

}
