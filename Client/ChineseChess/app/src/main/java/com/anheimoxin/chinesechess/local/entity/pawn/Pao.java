package com.anheimoxin.chinesechess.local.entity.pawn;

import com.anheimoxin.chinesechess.local.entity.pawn.base.Pawn;

public class Pao extends Pawn {
    public Pao(int color, int id, int camp, String name, int row, int col, boolean alive, boolean isSelected) {
        super(color, id, camp, name, row, col, alive, isSelected);
    }
    /**
     * 炮
     * 1.可以横着走
     * 2.可以竖着走
     * 3.可以跨越一个且仅一个棋子，并且目标位置一定要有对方的棋子
     */
    public static boolean canMove(Pawn[][] pawns, int rowFrom, int colFrom, int rowTo, int colTo, int moveTarget) {

        int count = 0;//计算当前位置与目的位置中间共有几个棋子存活

        if (rowFrom != rowTo && colFrom != colTo) {//行列不同，没戏
            return false;
        }

        //寻找中间跨越的棋子数量
        if (rowFrom == rowTo) {//同行
            int max = Math.max(colTo, colFrom);
            int min = Math.min(colTo, colFrom);

            for (int i = min + 1; i < max; ++i) {
                if (pawns[rowTo][i] != null) {
                    if (pawns[rowTo][i].isAlive()) {
                        ++count;
                    }
                }
            }
        } else if (colFrom == colTo) {//同列
            int max = Math.max(rowTo, rowFrom);
            int min = Math.min(rowTo, rowFrom);

            for (int i = min + 1; i < max; ++i) {
                if (pawns[i][colTo] != null) {
                    if (pawns[i][colTo].isAlive()) {
                        ++count;
                    }
                }
            }
        }

        //不能跨越两个以上的棋子，如果跨越棋子，则目标区域必须要有对方的棋子
        if (count < 2) {
            if (count == 0) {//炮只能隔一个棋子吃子，不能直接吃
                if (pawns[rowTo][colTo] != null) {
                    if (pawns[rowTo][colTo].isAlive()) {
                        return false;
                    }
                }
                return true;
            } else if (count == 1) {
                if (pawns[rowTo][colTo] != null) {
                    if (pawns[rowTo][colTo].isAlive()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
