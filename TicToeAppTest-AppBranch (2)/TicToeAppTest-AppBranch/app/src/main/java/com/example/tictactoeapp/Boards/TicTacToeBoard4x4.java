package com.example.tictactoeapp.Boards;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TicTacToeBoard4x4 extends View {
    private final Paint mDrawPaint = new Paint();
    private int cellSize;
    private int mPaintColor = Color.BLACK;
    private int gridType = 4;
    private final GameLogic4x4 game = new GameLogic4x4();
    private boolean winLine = false;

    public TicTacToeBoard4x4(Context context, AttributeSet attrs) {
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

        cellSize = dimension/4; // 4 boxes in each row & column
        Log.i("Testing: cellsize is = ", String.valueOf(cellSize));

        setMeasuredDimension(dimension, dimension); // To draw a Square
    }// onMeasure

    @Override
    protected void onDraw(Canvas canvas){
        drawGameBoard(canvas);
        drawMarkers(canvas);
    }// onDraw
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y/cellSize);
            int col = (int) Math.ceil(x/cellSize);

            if (!winLine){
                if (game.updateGameBoard(row,col)){
                    invalidate();

                    if(game.Winner()){
                        winLine = true;
                        invalidate();
                    }

                    //updating the players turn
                    if (game.getPlayer()% 2 ==0){
                        game.setPlayer(game.getPlayer()-1);
                    }
                    else {
                        game.setPlayer(game.getPlayer()+1);
                    }
                }
            }
            invalidate();
            return true;
        }
        return false;
    }

    private void drawGameBoard(Canvas canvas){
        //For loop to Create the board
        for (int col=1; col<gridType; col++){
            canvas.drawLine(cellSize*col, 0, cellSize*col,canvas.getWidth(), mDrawPaint);
        }
        for (int row=1; row<gridType; row++){
            canvas.drawLine(0, cellSize*row, canvas.getWidth(),cellSize*row, mDrawPaint);
        }
    }// drawGameBoard
    private void drawMarkers(Canvas canvas){
        for (int r=0; r<4; r++){
            for (int c=0; c<4; c++){
                if (game.getGameBoard()[r][c] != 0){
                    if (game.getGameBoard()[r][c] == 1){
                        DrawX(canvas,r,c);
                    }
                    else {
                        DrawO(canvas,r,c);
                    }
                }
            }
        }
    }

    //X and O pieces
    private void DrawX(Canvas canvas, int row , int col){
        mDrawPaint.setColor(Color.RED);

        // creates X
        canvas.drawLine((float) ((col+1)*cellSize - cellSize*0.2),
                (float) (row*cellSize +cellSize*0.2),
                (float) (col*cellSize +cellSize*0.2),
                (float) ((row+1)*cellSize -cellSize*0.2),
                mDrawPaint);

        canvas.drawLine((float) (col*cellSize + cellSize*0.2),
                (float) (row*cellSize + cellSize*0.2),
                (float) ((col+1)*cellSize - cellSize*0.2),
                (float) ((row+1)*cellSize - cellSize*0.2),
                mDrawPaint);
    }

    private void DrawO(Canvas canvas, int row , int col){
        mDrawPaint.setColor(Color.BLUE);

        canvas.drawOval((float) (col*cellSize + cellSize*0.2),
                (float) (row*cellSize + cellSize*0.2),
                (float) ((col*cellSize+ cellSize) -cellSize*0.2),
                (float) ((row*cellSize +cellSize) -cellSize*0.2),
                mDrawPaint);

    }
    //setup the game
    public void gameTime(Button playAgain, Button home, TextView playDisplay, String[] name){
        game.setPlayAgainBtn(playAgain);
        game.setHomeBtn(home);
        game.setPlayerTurn(playDisplay);
        game.setName(name);


    }

    public void resetGame(){
        game.resetGame();
        winLine = false;
    }

}
