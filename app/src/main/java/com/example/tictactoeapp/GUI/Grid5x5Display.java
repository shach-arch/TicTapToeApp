package com.example.tictactoeapp.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tictactoeapp.MainActivity;
import com.example.tictactoeapp.R;

public class Grid5x5Display extends AppCompatActivity {
    public int grid = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid5x5_display);

    }

    public void homeBtnClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public int getGrid(){
        return grid;
    }// getGrid
}