package com.example.tictactoeapp.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.example.tictactoeapp.MainActivity;
import com.example.tictactoeapp.R;

public class Grid3x3Display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid3x3_display);

        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String prefVal = sharedPref.getString("sync_frequency", "-1");
        Toast.makeText(this, "sync_frequency: " + prefVal, Toast.LENGTH_SHORT).show();
    }

    public void homeBtnClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}