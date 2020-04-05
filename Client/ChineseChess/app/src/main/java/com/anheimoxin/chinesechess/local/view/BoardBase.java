package com.anheimoxin.chinesechess.local.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.anheimoxin.chinesechess.local.entity.Grid;
import com.anheimoxin.chinesechess.local.entity.base.Container;
import com.anheimoxin.chinesechess.local.entity.pawn.Bing;
import com.anheimoxin.chinesechess.local.entity.pawn.Che;
import com.anheimoxin.chinesechess.local.entity.pawn.Jiang;
import com.anheimoxin.chinesechess.local.entity.pawn.Ma;
import com.anheimoxin.chinesechess.local.entity.pawn.Pao;
import com.anheimoxin.chinesechess.local.entity.pawn.Shi;
import com.anheimoxin.chinesechess.local.entity.pawn.Xiang;
import com.anheimoxin.chinesechess.local.entity.pawn.base.Pawn;


public class BoardBase extends SurfaceView implements SurfaceHolder.Callback {

    private Container container;

    private static int userCamp = 0;//0 是红色 1 是黑色
    //网格
    private Grid grid;
    //棋子
    private static Pawn[][] pawns;

    private float boardWidth;//网格的宽高
    private float boardHeight;

    private float xMargin;
    private float yMargin;

    private int xDivide = 10;//划分比例
    private float divideUnit;//划分单元长度

    private int clickPair;//记录两次点击，将第一次点击位置的棋子移动到第二次点击的位置
    private static Point[] pointPair;

    private Context context;


    public BoardBase(Context context) {
        super(context);

        this.context = context;
    }

    public BoardBase(int user_camp, Context context, float width, float height) {
        super(context);

        this.context = context;

        clickPair = 0;
        pointPair = new Point[2];
        pointPair[0] = new Point(-1, -1);
        pointPair[1] = new Point(-1, -1);

        userCamp = user_camp;

        pawns = createDefaultPawns();

        this.boardWidth = width;
        this.boardHeight = height;

        container = new Container();
        getHolder().addCallback(this);

        //初始化棋子,绘制网格
        initView();

        //监听点击事件
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTouchView(event.getX(), event.getY());
                if (canMovePawn(pawns, pointPair[0].row, pointPair[0].col, pointPair[1].row, pointPair[1].col)) {//判断能否将棋子从R行C列移动到r行c列
                    movePawn(pawns, pointPair[0].row, pointPair[0].col, pointPair[1].row, pointPair[1].col);
                }
                draw();
                performClick();
                return false;
            }
        });
    }

    public BoardBase(int user_camp, Pawn[][] pawn, Context context, float width, float height) {
        super(context);

        this.context = context;

        clickPair = 0;
        pointPair = new Point[2];
        pointPair[0] = new Point(-1, -1);
        pointPair[1] = new Point(-1, -1);

        userCamp = user_camp;

        pawns = pawn;

        this.boardWidth = width;
        this.boardHeight = height;

        container = new Container();
        getHolder().addCallback(this);

        //初始化棋子,绘制网格
        initView();

        //监听点击事件
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTouchView(event.getX(), event.getY());
                if (canMovePawn(pawns, pointPair[0].row, pointPair[0].col, pointPair[1].row, pointPair[1].col)) {
                    movePawn(pawns, pointPair[0].row, pointPair[0].col, pointPair[1].row, pointPair[1].col);
                }
                draw();
                performClick();
                return false;
            }
        });
    }

    //创建一个初始化的棋子集合
    public Pawn[][] createDefaultPawns() {
        Pawn[][] pawns = new Pawn[10][9];
        for (int r = 0; r < 10; ++r) {
            for (int c = 0; c < 9; ++c) {
                pawns[r][c] = new Pawn(Color.TRANSPARENT, -1, -1, "", r, c, false, false);
            }
        }
        //黑棋
        pawns[0][0] = new Che(Color.BLACK, 16, 1, "車", 0, 0, true, false);
        pawns[0][1] = new Ma(Color.BLACK, 17, 1, "馬", 0, 1, true, false);
        pawns[0][2] = new Xiang(Color.BLACK, 18, 1, "象", 0, 2, true, false);
        pawns[0][3] = new Shi(Color.BLACK, 19, 1, "士", 0, 3, true, false);
        pawns[0][4] = new Jiang(Color.BLACK, 20, 1, "将", 0, 4, true, false);
        pawns[0][5] = new Shi(Color.BLACK, 21, 1, "士", 0, 5, true, false);
        pawns[0][6] = new Xiang(Color.BLACK, 22, 1, "象", 0, 6, true, false);
        pawns[0][7] = new Ma(Color.BLACK, 23, 1, "馬", 0, 7, true, false);
        pawns[0][8] = new Che(Color.BLACK, 24, 1, "車", 0, 8, true, false);
        pawns[2][1] = new Pao(Color.BLACK, 25, 1, "炮", 2, 1, true, false);
        pawns[2][7] = new Bing(Color.BLACK, 26, 1, "炮", 2, 7, true, false);
        pawns[3][0] = new Bing(Color.BLACK, 27, 1, "卒", 3, 0, true, false);
        pawns[3][2] = new Bing(Color.BLACK, 28, 1, "卒", 3, 2, true, false);
        pawns[3][4] = new Bing(Color.BLACK, 29, 1, "卒", 3, 4, true, false);
        pawns[3][6] = new Bing(Color.BLACK, 30, 1, "卒", 3, 6, true, false);
        pawns[3][8] = new Bing(Color.BLACK, 31, 1, "卒", 3, 8, true, false);
        //红棋
        pawns[9][0] = new Che(Color.RED, 0, 0, "車", 9, 0, true, false);
        pawns[9][1] = new Ma(Color.RED, 1, 0, "馬", 9, 1, true, false);
        pawns[9][2] = new Xiang(Color.RED, 2, 0, "相", 9, 2, true, false);
        pawns[9][3] = new Shi(Color.RED, 3, 0, "仕", 9, 3, true, false);
        pawns[9][4] = new Jiang(Color.RED, 4, 0, "帅", 9, 4, true, false);
        pawns[9][5] = new Shi(Color.RED, 5, 0, "仕", 9, 5, true, false);
        pawns[9][6] = new Xiang(Color.RED, 6, 0, "相", 9, 6, true, false);
        pawns[9][7] = new Ma(Color.RED, 7, 0, "馬", 9, 7, true, false);
        pawns[9][8] = new Che(Color.RED, 8, 0, "車", 9, 8, true, false);
        pawns[7][1] = new Pao(Color.RED, 9, 0, "炮", 7, 1, true, false);
        pawns[7][7] = new Pao(Color.RED, 10, 0, "炮", 7, 7, true, false);
        pawns[6][0] = new Bing(Color.RED, 11, 0, "兵", 6, 0, true, false);
        pawns[6][2] = new Bing(Color.RED, 12, 0, "兵", 6, 2, true, false);
        pawns[6][4] = new Bing(Color.RED, 13, 0, "兵", 6, 4, true, false);
        pawns[6][6] = new Bing(Color.RED, 14, 0, "兵", 6, 6, true, false);
        pawns[6][8] = new Bing(Color.RED, 15, 0, "兵", 6, 8, true, false);

        return pawns;
    }

    /**
     * 1.不能移到棋盘外,不能原地踏步
     * 2.不能移到同阵营的棋子上面
     * 3.不能违反棋子本身的移动规则
     */
    public boolean canMovePawn(Pawn[][] pawns, int rowFrom, int colFrom, int rowTo, int colTo) {
        //1
        if (rowFrom < 0 || rowFrom > 9 || colFrom < 0 || colFrom > 8) {
            return false;
        }
        if (rowTo < 0 || rowTo > 9 || colTo < 0 || colTo > 8) {
            return false;
        }
        if (rowFrom == rowTo && colFrom == colTo) {
            return false;
        }
        //2
        if (pawns[rowFrom][colFrom] != null && pawns[rowTo][colTo] != null) {
            if (pawns[rowFrom][colFrom].getCamp() == pawns[rowTo][colTo].getCamp()) {
                return false;
            }
        }
        //3
        if (pawns[rowFrom][colFrom] != null) {

            //十位 为 行偏移
            //个位 为 列偏移
            int moveTarget = Math.abs((rowFrom - rowTo) * 10) + Math.abs(colFrom - colTo);
            //3
            switch (pawns[rowFrom][colFrom].getId()) {
                case 0://车
                case 8:
                case 16:
                case 24:
                    return Che.canMove(pawns, rowFrom, colFrom, rowTo, colTo, moveTarget);
                case 1://马
                case 7:
                case 17:
                case 23:
                    return Ma.canMove(pawns, rowFrom, colFrom, rowTo, colTo, moveTarget);
                case 2://相
                case 6:
                case 18:
                case 22:
                    return Xiang.canMove(pawns, rowFrom, colFrom, rowTo, colTo, moveTarget);
                case 3://仕
                case 5:
                case 19:
                case 21:
                    return Shi.canMove(pawns, rowFrom, colFrom, rowTo, colTo, moveTarget);
                case 4://将
                case 20:
                    return Jiang.canMove(pawns, rowFrom, colFrom, rowTo, colTo, moveTarget);
                case 9://炮
                case 10:
                case 25:
                case 26:
                    return Pao.canMove(pawns, rowFrom, colFrom, rowTo, colTo, moveTarget);
                case 11://兵
                case 12:
                case 13:
                case 14:
                case 15:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                    return Bing.canMove(pawns, rowFrom, colFrom, rowTo, colTo, moveTarget);
                default:
                    break;
            }
        }

        return false;
    }


    public void movePawn(Pawn[][] pawns, int rowFrom, int colFrom, int rowTo, int colTo) {
        if (rowFrom < 0 || rowFrom > 9 || colFrom < 0 || colFrom > 8) {
            return;
        }
        if (rowTo < 0 || rowTo > 9 || colTo < 0 || colTo > 8) {
            return;
        }
        if (pawns[rowFrom][colFrom] == null) {
            return;
        }

        container.removeChild(pawns[rowTo][colTo]);
        pawns[rowFrom][colFrom].setRow(rowTo);
        pawns[rowFrom][colFrom].setCol(colTo);

        pawns[rowTo][colTo] = pawns[rowFrom][colFrom];
        pawns[rowFrom][colFrom] = null;

        clickPair = 0;

        //记录一下棋子动向信息
        Grid.startR = rowFrom;
        Grid.startC = colFrom;
        Grid.endR = rowTo;
        Grid.endC = colTo;
    }

    private void initView() {
        divideUnit = boardWidth / xDivide;
        xMargin = divideUnit;//x的边距
        yMargin = (boardHeight - divideUnit * 9) / 2;//y的边距

        grid = new Grid(userCamp, xMargin, yMargin, 8 * divideUnit, 9 * divideUnit, divideUnit, Color.BLACK);
        container.addChild(grid);

        //绘制棋子
        Pawn.setXMargin(xMargin);
        Pawn.setYMargin(yMargin);
        Pawn.setRadius(divideUnit / 2 - 4);
        Pawn.setUnit(divideUnit);
        Pawn.setUserCamp(userCamp);

        for (int r = 0; r < 10; ++r) {
            for (int c = 0; c < 9; ++c) {
                container.addChild(pawns[r][c]);
            }
        }
    }

    public void onTouchView(float x, float y) {
        //点击View的位置，将其物理点击位置转换为逻辑点击位置，例如点击了View上坐标的（X,Y），则其逻辑点击位置为棋盘上的R行C列
        Point p = coordinatesToRC(x, y);
        onTouchBoard(pawns, p.row, p.col);
    }

    protected void onTouchBoard(Pawn[][] pawns, int row, int col) {
        //不要点出界
        if (row >= 10 || col >= 9 || row < 0 || col < 0) {
            return;
        }

        //将物理点击位置转换为逻辑点击位置
        if (userCamp == 1) {//如果是黑棋，需要转换坐标
            row = 9 - row;
            col = 8 - col;
        }

        if (clickPair == 0) {
            Grid.startR = -1;
            Grid.startC = -1;
            Grid.endR = -1;
            Grid.endC = -1;

            pointPair[0].row = -1;
            pointPair[0].col = -1;
            pointPair[1].row = -1;
            pointPair[1].col = -1;
        }

        if (clickPair == 0) {
            if (pawns[row][col] != null && pawns[row][col].getId() != -1) {
                Grid.startR = row;
                Grid.startC = col;
                Grid.endR = -1;
                Grid.endC = -1;

                pointPair[0].row = row;
                pointPair[0].col = col;
                clickPair = (clickPair + 1) % 2;
            }
        } else if (clickPair == 1) {

            pointPair[clickPair].row = row;
            pointPair[clickPair].col = col;
            clickPair = (clickPair + 1) % 2;

            //如果两次点击的位置相同则，取消选中
            if (pointPair[0].row == pointPair[1].row && pointPair[0].col == pointPair[1].col) {
                Grid.startR = -1;
                Grid.startC = -1;
                Grid.endR = -1;
                Grid.endC = -1;
            }

            //如果是选中新的棋子
            if (pawns[pointPair[0].row][pointPair[0].col] != null && pawns[pointPair[1].row][pointPair[1].col] != null) {
                if (pawns[pointPair[0].row][pointPair[0].col].getCamp() == pawns[pointPair[1].row][pointPair[1].col].getCamp()) {
                    clickPair = 1;
                    pointPair[0].row = row;
                    pointPair[0].col = col;
                    Grid.startR = row;
                    Grid.startC = col;
                    Grid.endR = -1;
                    Grid.endC = -1;

                }
            }
        }
    }

    //将点击的坐标转换为行列
    public Point coordinatesToRC(float x, float y) {
        Point point = new Point();

        point.col = Math.round((x - getxMargin()) / getDivideUnit());
        point.row = Math.round((y - getyMargin()) / getDivideUnit());

        return point;
    }

    public void draw() {
        Canvas canvas = getHolder().lockCanvas();
        if (canvas != null) {
            //棋盘背景色
            canvas.drawColor(Color.rgb(228, 188, 139));
            container.draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public float getGridsWidth() {
        return boardWidth;
    }

    public void setGridsWidth(float gridsWidth) {
        this.boardWidth = gridsWidth;
    }

    public float getGridsHeight() {
        return boardHeight;
    }

    public void setGridsHeight(float gridsHeight) {
        this.boardHeight = gridsHeight;
    }

    public float getxMargin() {
        return xMargin;
    }

    public void setxMargin(float xMargin) {
        this.xMargin = xMargin;
    }

    public float getyMargin() {
        return yMargin;
    }

    public void setyMargin(float yMargin) {
        this.yMargin = yMargin;
    }

    public int getxDivide() {
        return xDivide;
    }

    public void setxDivide(int xDivide) {
        this.xDivide = xDivide;
    }

    public float getDivideUnit() {
        return divideUnit;
    }

    public void setDivideUnit(float divideUnit) {
        this.divideUnit = divideUnit;
    }

    public static int getUserCamp() {
        return userCamp;
    }

    public static void setUserCamp(int camp) {
        userCamp = camp;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public static Pawn[][] getPawns() {
        return pawns;
    }

    public static void setPawns(Pawn[][] pawns) {
        BoardBase.pawns = pawns;
    }

    public int getClickPair() {
        return clickPair;
    }

    public void setClickPair(int clickPair) {
        this.clickPair = clickPair;
    }

    public static Point[] getPointPair() {
        return pointPair;
    }

    public static void setPointPair(Point[] pointPair) {
        BoardBase.pointPair = pointPair;
    }

    public static class Point {
        Point() {
            row = -1;
            col = -1;
        }

        Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        int row;
        int col;
    }


}
