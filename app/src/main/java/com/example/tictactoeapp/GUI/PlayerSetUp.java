package com.example.tictactoeapp.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tictactoeapp.R;

public class PlayerSetUp extends AppCompatActivity {
    EditText player1;
    EditText player2;
    String gameBoard;
    int gridType;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_setup);

        player1 = findViewById(R.id.player1_name);
        player2 = findViewById(R.id.player2_name);

        Spinner spinControl = (Spinner) findViewById(R.id.grid_spinner);
        ArrayAdapter<CharSequence> gridList = ArrayAdapter.createFromResource(this, R.array.grid_array,
                android.R.layout.simple_spinner_item);
        gridList.setDropDownViewResource(android.R.layout .simple_spinner_dropdown_item);
        spinControl.setAdapter(gridList);

        //spinControl.setSelection(((ArrayAdapter)spinControl.getAdapter()).getPosition());

        spinControl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "Selected board: " + String.valueOf(spinControl.getSelectedItem()), Toast.LENGTH_LONG).show();
                gameBoard = String.valueOf(spinControl.getSelectedItem());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }// onCreate

    public void startButtonClick(View view){
        String player1Name = player1.getText().toString();
        String player2Name = player2.getText().toString();

        //Opens a different GameBoard-display based on users choice
        switch (gameBoard){
            case "3 x 3":
                intent = new Intent(this, Grid3x3Display.class);
                intent.putExtra("PLAYER_NAMES", new String[] {player1Name, player2Name});
                intent.putExtra("GRID", 3);
                startActivity(intent);
                break;
            case "4 x 4":
                intent = new Intent(this, Grid4x4Display.class);
                intent.putExtra("PLAYER_NAMES", new String[] {player1Name, player2Name});
                intent.putExtra("GRID", 4);
                startActivity(intent);
                break;
            case "5 x 5":
                intent = new Intent(this, Grid5x5Display.class);
                intent.putExtra("PLAYER_NAMES", new String[] {player1Name, player2Name});
                startActivity(intent);
                break;
        }
    }// startButtonClick
}