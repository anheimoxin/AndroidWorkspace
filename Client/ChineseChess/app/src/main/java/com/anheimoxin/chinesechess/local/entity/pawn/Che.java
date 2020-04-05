package com.anheimoxin.chinesechess.local.entity.pawn;

import com.anheimoxin.chinesechess.local.entity.pawn.base.Pawn;

public class Che extends Pawn {
    public Che(int color, int id, int camp, String name, int row, int col, boolean alive, boolean isSelected) {
        super(color, id, camp, name, row, col, alive, isSelected);
    }

    /**
     * 车
     * 1.可以竖着走
     * 2.可以横着走
     * 3.不可以跨越棋子
     */
    public static boolean canMove(Pawn[][] pawns, int rowFrom, int colFrom, int rowTo, int colTo, int moveTarget) {
        if (rowFrom != rowTo && colFrom != colTo) {//行列不同，没戏
            return false;
        }

        if (rowFrom == rowTo) {//同行
            int max = Math.max(colTo, colFrom);
            int min = Math.min(colTo, colFrom);

            for (int i = min + 1; i < max; ++i) {
                if (pawns[rowTo][i] != null) {
                    if (pawns[rowTo][i].isAlive()) {
                        //同一行上，两者之间，有存活的棋子，不能移动
                        return false;
                    }
                }
            }
            return true;
        } else if (colFrom == colTo) {//同列
            int max = Math.max(rowTo, rowFrom);
            int min = Math.min(rowTo, rowFrom);

            for (int i = min + 1; i < max; ++i) {
                if (pawns[i][colTo] != null) {
                    if (pawns[i][colTo].isAlive()) {
                        //同一列上，两者之间，有存活的棋子，不能移动
                        return false;
                    }
                }
            }
            return true;
        }

        return false;
    }
}
