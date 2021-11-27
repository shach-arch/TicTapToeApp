package com.drawingtest.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.drawingtest.R;
import com.drawingtest.domain.manager.FileManager;
import com.drawingtest.domain.manager.PermissionManager;
import com.drawingtest.ui.component.DrawingView;
import com.drawingtest.ui.dialog.NumberPickerDialog;
import com.drawingtest.ui.dialog.StrokeSelectorDialog;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    @Bind(R.id.drawingView) DrawingView mDrawingScreen;

    private int mCurrentBackgroundColor;
    private int mCurrentColor;
    private int mCurrentStroke;
    private static final int MAX_STROKE_WIDTH = 50;
    private int mCurrentShape;

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ButterKnife.bind(this);
        initDrawingView();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.main_fill_iv:
                    startFillBackgroundDialog();
                    return true;
                case R.id.main_color_iv:
                    startColorPickerDialog();
                    return true;
                case R.id.main_stroke_iv:
                    startStrokeSelectorDialog();
                    return true;
                case R.id.main_undo_iv:
                    mDrawingScreen.undo();
                    return true;
                case R.id.main_redo_iv:
                    mDrawingScreen.redo();
                    return true;
            }

            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                requestPermissionsAndSaveBitmap();
                break;
            case R.id.action_clear:
                mDrawingScreen.clearCanvas();
                break;
            case R.id.action_shape:
                showNumberPicker();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        Toast.makeText(this,
                "selected number " + numberPicker.getValue(), Toast.LENGTH_SHORT).show();

        mCurrentShape = newVal;
        mDrawingScreen.setShape(mCurrentShape);
    }// onValueChange

    public void showNumberPicker(){
        NumberPickerDialog newFragment = new NumberPickerDialog();
        newFragment.setValueChangeListener(this);
        newFragment.show(getFragmentManager(), "time picker");
    }// showNumberPicker

    private void initDrawingView() {
        mCurrentBackgroundColor = ContextCompat.getColor(this, android.R.color.white);
        mCurrentColor = ContextCompat.getColor(this, android.R.color.black);
        mCurrentStroke = 10;

        mDrawingScreen.setBackgroundColor(mCurrentBackgroundColor);
        mDrawingScreen.setPaintColor(mCurrentColor);
        mDrawingScreen.setPaintStrokeWidth(mCurrentStroke);
    }// initDrawingView

    private void startFillBackgroundDialog()
    {
        int[] colors = getResources().getIntArray(R.array.palette);

        ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                colors,
                mCurrentBackgroundColor,
                5,
                ColorPickerDialog.SIZE_SMALL);

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener()
        {

            @Override
            public void onColorSelected(int color)
            {
                mCurrentBackgroundColor = color;
                mDrawingScreen.setBackgroundColor(mCurrentBackgroundColor);
            }

        });

        dialog.show(getFragmentManager(), "ColorPickerDialog");
    }// startFillBackgroundDialog

    private void startColorPickerDialog()
    {
        int[] colors = getResources().getIntArray(R.array.palette);

        ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                colors,
                mCurrentColor,
                5,
                ColorPickerDialog.SIZE_SMALL);

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener()
        {

            @Override
            public void onColorSelected(int color)
            {
                mCurrentColor = color;
                mDrawingScreen.setPaintColor(mCurrentColor);
            }

        });

        dialog.show(getFragmentManager(), "ColorPickerDialog");
    }// startColorPickerDialog

    private void startStrokeSelectorDialog()
    {
        StrokeSelectorDialog dialog = StrokeSelectorDialog.newInstance(mCurrentStroke, MAX_STROKE_WIDTH);

        dialog.setOnStrokeSelectedListener(new StrokeSelectorDialog.OnStrokeSelectedListener()
        {
            @Override
            public void onStrokeSelected(int stroke)
            {
                mCurrentStroke = stroke;
                mDrawingScreen.setPaintStrokeWidth(mCurrentStroke);
            }
        });

        dialog.show(getSupportFragmentManager(), "StrokeSelectorDialog");
    }// startStrokeSelectorDialog

    private void startShareDialog(Uri uri)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share Image"));
    }// startShareDialog

    private void requestPermissionsAndSaveBitmap()
    {
        if (PermissionManager.checkWriteStoragePermissions(this))
        {
            Uri uri = FileManager.saveBitmap(mDrawingScreen.getBitmap());
            startShareDialog(uri);
        }
    }// requestPermissionsAndSaveBitmap

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PermissionManager.REQUEST_WRITE_STORAGE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Uri uri = FileManager.saveBitmap(mDrawingScreen.getBitmap());
                    startShareDialog(uri);
                } else
                {
                    Toast.makeText(this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
