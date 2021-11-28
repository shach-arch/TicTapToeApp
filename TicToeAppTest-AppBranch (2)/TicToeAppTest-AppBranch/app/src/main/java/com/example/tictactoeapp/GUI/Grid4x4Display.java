package com.example.tictactoeapp.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tictactoeapp.Boards.TicTacToeBoard4x4;
import com.example.tictactoeapp.MainActivity;
import com.example.tictactoeapp.R;

import butterknife.ButterKnife;

public class Grid4x4Display extends AppCompatActivity {

    private TicTacToeBoard4x4 ticTacToeBoard4x4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid4x4_display);
        Button playAgainBtn = findViewById(R.id.play_again_button);
        Button homeBtn = findViewById(R.id.go_home_button);
        TextView playerTurn = findViewById(R.id.current_player_TV);

        playAgainBtn.setVisibility(View.GONE);
        homeBtn.setVisibility(View.GONE);


        String[] playerNames = getIntent().getStringArrayExtra("PLAYER_NAMES");
        if (playerNames != null){
            playerTurn.setText(("Current Player"+" "+playerNames[0] + "'s Turn"));
        }

        ticTacToeBoard4x4 = findViewById(R.id.ticTacToeBoard4x4);

        ticTacToeBoard4x4.gameTime(playAgainBtn,homeBtn,playerTurn,playerNames);
        
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String prefVal = sharedPref.getString("sync_frequency", "-1");
        Toast.makeText(this, "sync_frequency: " + prefVal, Toast.LENGTH_SHORT).show();

        ButterKnife.bind(this);


    }

    public void playAgainBtnClick(View view){
        ticTacToeBoard4x4.resetGame();
        ticTacToeBoard4x4.invalidate();
    }//playAgainBtnClick

    public void homeBtnClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }//homeBtnClick
}