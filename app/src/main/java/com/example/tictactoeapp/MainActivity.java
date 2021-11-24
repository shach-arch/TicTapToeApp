package com.example.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tictactoeapp.GUI.PlayerSetUp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity_main);
    }

    public void playButtonClick(View view) {
        Intent intent = new Intent(this, PlayerSetUp.class);
        startActivity(intent);
    }
}