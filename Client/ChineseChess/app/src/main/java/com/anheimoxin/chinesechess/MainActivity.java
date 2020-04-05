package com.anheimoxin.chinesechess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anheimoxin.chinesechess.local.LocalGameActivity;

public class MainActivity extends AppCompatActivity {
    private Button localGameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localGameBtn = findViewById(R.id.btn_local_game);
        localGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalGameActivity.actionStart(MainActivity.this);
            }
        });
    }
}
