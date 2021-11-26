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

import com.example.tictactoeapp.MainActivity;
import com.example.tictactoeapp.R;
import com.example.tictactoeapp.Boards.TicTacToeBoard;
import butterknife.Bind;
import butterknife.ButterKnife;

public class Grid3x3Display extends AppCompatActivity {

    private TicTacToeBoard ticTacToeBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid3x3_display);


        Button playAgainBtn = findViewById(R.id.play_again_button);
        Button homeBtn = findViewById(R.id.go_home_button);
        TextView playerTurn = findViewById(R.id.current_player_TV);

        playAgainBtn.setVisibility(View.GONE);
        homeBtn.setVisibility(View.GONE);


        String[] playerNames = getIntent().getStringArrayExtra("PLAYER_NAMES");
        assert playerNames != null;
        if (playerNames[0].equals("")){

            playerTurn.setText("Player 1's turn");
        }else{
            playerTurn.setText(("Current Player"+" "+playerNames[0] + "'s turn"));
        }
        ticTacToeBoard = findViewById(R.id.ticTacToeBoard_3);

        ticTacToeBoard.gameTime(playAgainBtn,homeBtn,playerTurn,playerNames);


        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String prefVal = sharedPref.getString("sync_frequency", "-1");
        Toast.makeText(this, "sync_frequency: " + prefVal, Toast.LENGTH_SHORT).show();
    }

    public void playAgainBtnClick(View view){
        ticTacToeBoard.resetGame();
        ticTacToeBoard.invalidate();
    }//playAgainBtnClick

    public void homeBtnClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }// homeBtnClick
}