package com.example.tictactoeapp.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.tictactoeapp.R;

public class PlayerChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_setup);

        Spinner spinner = (Spinner) findViewById(R.id.grid_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.grid_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout .simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setSelection(((ArrayAdapter) mTitleSpinner.getAdapter()).getPosition(mCrime.getTitle()));
        //spinner.setOnItemSelectedListener(this);
    }
}