package com.anheimoxin.chinesechess.local.entity.pawn;

import com.anheimoxin.chinesechess.local.entity.pawn.base.Pawn;

public class Ma extends Pawn {
    public Ma(int color, int id, int camp, String name, int row, int col, boolean alive, boolean isSelected) {
        super(color, id, camp, name, row, col, alive, isSelected);
    }

    /**
     * 马
     * 1.可以走“日”,所有的可能位置共8个
     * 2.会被塞马脚
     */
    public static boolean canMove(Pawn[][] pawns, int rowFrom, int colFrom, int rowTo, int colTo, int moveTarget) {
        //1
        if (moveTarget == 21) {
            //2
            Pawn p = pawns[(rowFrom + rowTo) / 2][colFrom];
            if (p == null) {
                return true;
            } else if (!p.isAlive()) {
                return true;
            }

        } else if (moveTarget == 12) {
            //2
            Pawn p = pawns[rowFrom][(colFrom + colTo) / 2];
            if (p == null) {
                return true;
            } else if (!p.isAlive()) {
                return true;
            }
        }
        return false;
    }

}
