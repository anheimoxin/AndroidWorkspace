package com.anheimoxin.chinesechess.local.entity.pawn.base;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.anheimoxin.chinesechess.local.entity.base.Container;


/**
 * 棋子类
 * 一个棋子拥有唯一的标记id，阵营，名字，存活状态，是否被选中
 */
public class Pawn extends Container implements Cloneable {

    private Paint paint;
    private int color;

    private static float xMargin = 10;
    private static float yMargin = 10;

    private static float radius = 10;
    private static float unit = 10;

    private static int userCamp = -1;

    private int id;                 //id
    private int camp;               //阵营 0 红 1 黑
    private String name;            //名字 ，兵 卒
    private boolean alive;          //是否存活
    private boolean isSelected;     //是否被选中
    private int row;                //棋子所在行
    private int col;//棋子所在列

    public Pawn(int color, int id, int camp, String name, int row, int col, boolean alive, boolean isSelected) {
        paint = new Paint();

        this.color = color;
        this.id = id;
        this.camp = camp;
        this.name = name;
        this.row = row;
        this.col = col;
        this.alive = alive;
        this.isSelected = isSelected;

    }

    @Override
    public void drawChild(Canvas canvas) {
        if (!alive) {//不画死棋
            return;
        }

        if (id == -1) {
            return;
        }

        //重新设定偏移
        if (userCamp == 1) {//如果玩家是黑棋，则棋盘旋转180度，中心对称
            canvas.translate(getXMargin() + (8 - col) * unit, getYMargin() + (9 - row) * unit);
        } else {
            canvas.translate(getXMargin() + col * unit, getYMargin() + row * unit);
        }

        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawCircle(0, 0, radius, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(unit / 2);
        canvas.drawText(name, -unit / 4, unit / 6, paint);

        paint.setColor(Color.RED);
        if (isSelected) {//如果棋子被选中了，则给它画一个选中的标记
            drawMark(canvas, row, col);
        }
    }

    /**
     * 画标记
     *
     * @param canvas
     * @param r      行
     * @param c      列
     */
    private void drawMark(Canvas canvas, int r, int c) {
        if (userCamp == 1) {//玩家是黑方，坐标需要进行转换
            c = 8 - c;
            r = 9 - r;
        }

        //保存一下以前的color
        int oldColor = paint.getColor();

        paint.setColor(Color.RED);

        drawMarkPart(canvas, 0, 0, 0);
        drawMarkPart(canvas, 1, 0, 0);
        drawMarkPart(canvas, 2, 0, 0);
        drawMarkPart(canvas, 3, 0, 0);

        //恢复以前的color
        paint.setColor(oldColor);
    }

    //画一个角,type == 0 正方形的左上 type == 1 正方形的右下
    private void drawMarkPart(Canvas canvas, int direction, int r, int c) {
        int lineLength = (int) (unit / 6);
        int offset = 0;
        int startX = 0;
        int startY = 0;

        offset = (int) (unit / 2 - 4);
        lineLength = -lineLength;

        switch (direction) {
            case 0:
                startX = (int) (0 + c * unit - offset);
                startY = (int) (0 + r * unit - offset);
                canvas.drawLine(startX, startY, startX - lineLength, startY, paint);
                canvas.drawLine(startX, startY, startX, startY - lineLength, paint);
                break;
            case 1:
                startX = (int) (0 + c * unit + offset);
                startY = (int) (0 + r * unit - offset);
                canvas.drawLine(startX, startY, startX + lineLength, startY, paint);
                canvas.drawLine(startX, startY, startX, startY - lineLength, paint);
                break;
            case 2:
                startX = (int) (0 + c * unit + offset);
                startY = (int) (0 + r * unit + offset);
                canvas.drawLine(startX, startY, startX + lineLength, startY, paint);
                canvas.drawLine(startX, startY, startX, startY + lineLength, paint);
                break;
            case 3:
                startX = (int) (0 + c * unit - offset);
                startY = (int) (0 + r * unit + offset);
                canvas.drawLine(startX, startY, startX - lineLength, startY, paint);
                canvas.drawLine(startX, startY, startX, startY + lineLength, paint);
                break;
            default:
                break;
        }
    }

    //拷贝内存
    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static float getXMargin() {
        return xMargin;
    }

    public static void setXMargin(float _xMargin) {
        xMargin = _xMargin;
    }

    public static float getYMargin() {
        return yMargin;
    }

    public static void setYMargin(float _yMargin) {
        yMargin = _yMargin;
    }

    public static float getRadius() {
        return radius;
    }

    public static void setRadius(float _radius) {
        radius = _radius;
    }

    public static float getUnit() {
        return unit;
    }

    public static void setUnit(float _unit) {
        unit = _unit;
    }

    public static int getUserCamp() {
        return userCamp;
    }

    public static void setUserCamp(int _userCamp) {
        userCamp = _userCamp;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCamp() {
        return camp;
    }

    public void setCamp(int camp) {
        this.camp = camp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
