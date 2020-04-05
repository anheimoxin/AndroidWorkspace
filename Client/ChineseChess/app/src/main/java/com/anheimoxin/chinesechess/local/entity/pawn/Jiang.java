package com.anheimoxin.chinesechess.local.entity.pawn;

import com.anheimoxin.chinesechess.local.entity.pawn.base.Pawn;

public class Jiang extends Pawn {
    public Jiang(int color, int id, int camp, String name, int row, int col, boolean alive, boolean isSelected) {
        super(color, id, camp, name, row, col, alive, isSelected);
    }

    public static boolean canMove(Pawn[][] pawns, int rowFrom, int colFrom, int rowTo, int colTo, int moveTarget) {
        //可以飞将
        if (moveTarget == 90 || moveTarget == 80 || moveTarget == 70 || moveTarget == 60 || moveTarget == 50) {
            if (colFrom == colTo) {//同列
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

                //目标棋子必须是将帅才能飞将
                if (pawns[rowTo][colTo] != null) {
                    if (pawns[rowFrom][colFrom].getId() == 4) {
                        return pawns[rowTo][colTo].getId() == 20;
                    } else if (pawns[rowFrom][colFrom].getId() == 20) {
                        return pawns[rowTo][colTo].getId() == 4;
                    }
                }

                return false;
            }
        }

        //将帅不能出田字格
        if (moveTarget == 1 || moveTarget == 10) {
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
