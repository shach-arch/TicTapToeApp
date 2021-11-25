package com.example.tictactoeapp.Boards;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class TicTacToeBoard5x5 extends View {
    private final Paint mDrawPaint = new Paint();
    private int cellSize;
    private int mPaintColor = Color.BLACK;
    private int gridType = 5;

    public TicTacToeBoard5x5(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint()
    {
        mDrawPaint.setStyle(Paint.Style.STROKE);
        mDrawPaint.setAntiAlias(true);
        mDrawPaint.setColor(mPaintColor);
        mDrawPaint.setStrokeWidth(16);
    }

    public void setGridType(int newNum){
        gridType = newNum;
    }

    // Set Dimensions of Board
    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        //find the smallest dimension of the Device
        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        Log.i("Testing: dimension is =", String.valueOf(dimension));

        cellSize = dimension/5; // 4 boxes in each row & column
        Log.i("Testing: cellsize is = ", String.valueOf(cellSize));

        setMeasuredDimension(dimension, dimension); // To draw a Square
    }// onMeasure

    @Override
    protected void onDraw(Canvas canvas){
        drawGameBoard(canvas);
    }// onDraw

    private void drawGameBoard(Canvas canvas){
        //For loop to Create the board
        for (int col=1; col<5; col++){
            canvas.drawLine(cellSize*col, 0, cellSize*col,canvas.getWidth(), mDrawPaint);
        }
        for (int row=1; row<5; row++){
            canvas.drawLine(0, cellSize*row, canvas.getWidth(),cellSize*row, mDrawPaint);
        }
    }// drawGameBoard

}
