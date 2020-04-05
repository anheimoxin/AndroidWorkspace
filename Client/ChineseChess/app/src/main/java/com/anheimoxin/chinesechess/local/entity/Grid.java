package com.anheimoxin.chinesechess.local.entity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.anheimoxin.chinesechess.local.entity.base.Container;

/**
 * 五子棋 棋盘网格 类，只是用来绘制网格的
 */
public class Grid extends Container {
    //用户的阵营不同，则绘制的中心对称不同
    private int userCamp;

    private Paint paint;
    private int color;

    private float xMargin;//偏移点 起始
    private float yMargin;

    private float width;
    private float height;

    private float unit;

    public static int startR = -1;  //记录起始位置
    public static int startC = -1;  //记录起始位置
    public static int endR = -1;  //记录终点位置
    public static int endC = -1;  //记录终点位置

    public Grid(int userCamp, float xMargin, float yMargin, float width, float height, float unit, int color) {
        this.userCamp = userCamp;

        paint = new Paint();
        this.color = color;

        this.xMargin = xMargin;
        this.yMargin = yMargin;
        this.width = width;
        this.height = height;

        this.unit = unit;

        //重置记录
        startR = -1;  //记录起始位置
        startC = -1;  //记录起始位置
        endR = -1;  //记录终点位置
        endC = -1;  //记录终点位置
    }

    @Override
    public void drawChild(Canvas canvas) {
        //重新设定偏移
        canvas.translate(getXMargin(), getYMargin());

        paint.setAntiAlias(true);
        paint.setColor(color);

        //线条
        drawLines(canvas);
        //兵和炮的标记
        drawPawnMarks(canvas);
        //楚河汉界
        drawRiver(canvas);

        drawMoveTag(canvas);
    }

    private void drawMoveTag(Canvas canvas) {
        //画棋子的动向标记
        drawPawnMotion(canvas);
    }

    //画棋子的动向标记
    private void drawPawnMotion(Canvas canvas) {
        if (startR != -1 && endR != -1) {
            drawMark(canvas, 1, startR, startC);
            drawMark(canvas, 1, endR, endC);
        }

        if (startR != -1 && endR == -1) {
            drawMark(canvas, 1, startR, startC);
        }
    }

    private void drawRiver(Canvas canvas) {
        //画楚河汉界
        paint.setTextSize(unit / 3 * 2);
        paint.setColor(Color.rgb(110, 91, 67));
        if (userCamp == 0) {
            canvas.drawText("楚河", unit + unit / 2, 5 * unit - unit / 4, paint);
            canvas.drawText("汉界", width - (unit + unit / 2) - paint.getTextSize() * 2, 5 * unit - unit / 4, paint);
        } else if (userCamp == 1) {
            canvas.drawText("汉界", unit + unit / 2, 5 * unit - unit / 4, paint);
            canvas.drawText("楚河", width - (unit + unit / 2) - paint.getTextSize() * 2, 5 * unit - unit / 4, paint);
        }
    }

    private void drawLines(Canvas canvas) {

        //画竖线 外面的长 ，里边的短
        canvas.drawLine(0, 0, 0, 9 * unit, paint);
        canvas.drawLine(8 * unit, 0, 8 * unit, 9 * unit, paint);

        for (int i = 1; i < 8; i++) {
            canvas.drawLine(i * unit, 0, i * unit, 4 * unit, paint);
            canvas.drawLine(i * unit, 5 * unit, i * unit, 9 * unit, paint);
        }

        //画横线 10条
        for (int i = 0; i < 10; i++) {
            canvas.drawLine(0, i * unit, 8 * unit, i * unit, paint);
        }

        //画交叉线
        canvas.drawLine(3 * unit, 0, 5 * unit, 2 * unit, paint);
        canvas.drawLine(5 * unit, 0, 3 * unit, 2 * unit, paint);

        canvas.drawLine(3 * unit, 7 * unit, 5 * unit, 9 * unit, paint);
        canvas.drawLine(5 * unit, 7 * unit, 3 * unit, 9 * unit, paint);
    }

    private void drawPawnMarks(Canvas canvas) {
        //画炮和兵的初始位置的标记
        drawMark(canvas, 0, 2, 1);
        drawMark(canvas, 0, 2, 7);
        drawMark(canvas, 0, 3, 0);
        drawMark(canvas, 0, 3, 2);
        drawMark(canvas, 0, 3, 4);
        drawMark(canvas, 0, 3, 6);
        drawMark(canvas, 0, 3, 8);
        drawMark(canvas, 0, 7, 1);
        drawMark(canvas, 0, 7, 7);
        drawMark(canvas, 0, 6, 0);
        drawMark(canvas, 0, 6, 2);
        drawMark(canvas, 0, 6, 4);
        drawMark(canvas, 0, 6, 6);
        drawMark(canvas, 0, 6, 8);
    }

    /**
     * 画标记
     *
     * @param canvas
     * @param type   0是向内角，1是向外角
     * @param r
     * @param c
     */
    private void drawMark(Canvas canvas, int type, int r, int c) {
        if (userCamp == 1) {//玩家是黑方，坐标需要进行转换
            c = 8 - c;
            r = 9 - r;
        }

        //保存一下以前的color
        int oldColor = paint.getColor();
        if (type == 1) {
            paint.setColor(Color.RED);
        }
        //有四个兵的位置标记只有一半
        if (type == 0) {
            if ((r == 3 && c == 0) || (r == 6 && c == 0)) {
                drawMarkPart(canvas, type, 1, r, c);
                drawMarkPart(canvas, type, 2, r, c);
                return;
            } else if ((r == 3 && c == 8) || (r == 6 && c == 8)) {
                drawMarkPart(canvas, type, 0, r, c);
                drawMarkPart(canvas, type, 3, r, c);
                return;
            }
        }
        drawMarkPart(canvas, type, 0, r, c);
        drawMarkPart(canvas, type, 1, r, c);
        drawMarkPart(canvas, type, 2, r, c);
        drawMarkPart(canvas, type, 3, r, c);

        //恢复一下以前的color
        paint.setColor(oldColor);
    }

    //画一个角,type == 0 正方形的左上 type == 1 正方形的右下
    private void drawMarkPart(Canvas canvas, int type, int direction, int r, int c) {
        int lineLength = (int) (unit / 6);
        int offset = 0;
        int startX = 0;
        int startY = 0;

        switch (type) {
            case 0:
                offset = (int) (unit / 20);
                break;
            case 1:
                offset = (int) (unit / 2 - 4);
                lineLength = -lineLength;
                break;
            default:
                break;
        }

        switch (direction) {
            case 0:
                startX = (int) (c * unit - offset);
                startY = (int) (r * unit - offset);
                canvas.drawLine(startX, startY, startX - lineLength, startY, paint);
                canvas.drawLine(startX, startY, startX, startY - lineLength, paint);
                break;
            case 1:
                startX = (int) (c * unit + offset);
                startY = (int) (r * unit - offset);
                canvas.drawLine(startX, startY, startX + lineLength, startY, paint);
                canvas.drawLine(startX, startY, startX, startY - lineLength, paint);
                break;
            case 2:
                startX = (int) (c * unit + offset);
                startY = (int) (r * unit + offset);
                canvas.drawLine(startX, startY, startX + lineLength, startY, paint);
                canvas.drawLine(startX, startY, startX, startY + lineLength, paint);
                break;
            case 3:
                startX = (int) (c * unit - offset);
                startY = (int) (r * unit + offset);
                canvas.drawLine(startX, startY, startX - lineLength, startY, paint);
                canvas.drawLine(startX, startY, startX, startY + lineLength, paint);
                break;
            default:
                break;
        }
    }


    public float getXMargin() {
        return xMargin;
    }

    public void setXMargin(float xMargin) {
        this.xMargin = xMargin;
    }

    public float getYMargin() {
        return yMargin;
    }

    public void setYMargin(float yMargin) {
        this.yMargin = yMargin;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
