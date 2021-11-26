package com.example.tictactoeapp.Boards;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TicTacToeBoard extends View {
    private final Paint mDrawPaint = new Paint();
    private int cellSize;
    private int mPaintColor = Color.BLACK;
    private final GameLogic3x3 game = new GameLogic3x3();
    private boolean winLine = false;

    public TicTacToeBoard(Context context, AttributeSet attrs) {
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

    // Set Dimensions of Board
    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        //find the smallest dimension of the Device
        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        Log.i("Testing: dimension is =", String.valueOf(dimension));

        cellSize = dimension/3; // 3 boxes in each row & column
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
        for (int col=1; col<3; col++){
            canvas.drawLine(cellSize*col, 0, cellSize*col,canvas.getWidth(), mDrawPaint);
        }
        for (int row=1; row<3; row++){
            canvas.drawLine(0, cellSize*row, canvas.getWidth(),cellSize*row, mDrawPaint);
        }
    }// drawGameBoard

    private void drawMarkers(Canvas canvas){
        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++){
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
    public void gameTime(Button playAgain,Button home,TextView playDisplay,String[] name){
        game.setPlayAgainBtn(playAgain);
        game.setHomeBtn(home);
        game.setPlayerTurn(playDisplay);
        if (!name[0].equals("") && !name[1].equals("")){
            game.setName(name);
        }
    }

    public void resetGame(){
        game.resetGame();
        winLine = false;
    }
}
