package com.anheimoxin.chinesechess.local;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.anheimoxin.chinesechess.R;
import com.anheimoxin.chinesechess.local.view.BoardBase;


public class LocalGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_game);

        initUI();
    }

    private void initUI() {
        //绑定界面的控件
        CoordinatorLayout layout = findViewById(R.id.layout_local_game);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //获取布局的宽高
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        //user_camp=1,则玩家是黑棋，user_camp=0,则玩家是红棋
        BoardBase board = new BoardBase(1, this, width, height);

        board.setMinimumWidth(width);
        board.setMinimumHeight(height);
        board.setFitsSystemWindows(true);

        //将BoardBase添加到布局中
        layout.addView(board);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LocalGameActivity.class);
        context.startActivity(intent);
    }

}
