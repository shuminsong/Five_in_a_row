package com.example.fiveinarow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class test extends AppCompatActivity {
    private Chessboard chessboard;
    private Button restartgame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        chessboard = (Chessboard) findViewById(R.id.board);

        restartgame = (Button) findViewById(R.id.restartgame);
        restartgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chessboard.restart();
            }
        });
    }
}
