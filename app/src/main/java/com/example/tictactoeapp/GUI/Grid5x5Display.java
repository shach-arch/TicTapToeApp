package com.example.tictactoeapp.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tictactoeapp.Boards.TicTacToeBoard5x5;
import com.example.tictactoeapp.MainActivity;
import com.example.tictactoeapp.R;

public class Grid5x5Display extends AppCompatActivity {
    private TicTacToeBoard5x5 ticTacToeBoard5x5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid5x5_display);

    }

    public void playAgainBtnClick(View view){
        //ticTacToeBoard5x5.resetGame();
        //ticTacToeBoard5x5.invalidate();
    }//playAgainBtnClick

    public void homeBtnClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }// homeBtnClick
}