package com.example.tictactoeapp.Boards;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic4x4 {
    private int [][] gameBoard;

    private Button playAgainBtn;
    private Button homeBtn;
    private TextView playerTurn;
    private String[] name = {"Player 1","Player 2"};

    private int player = 1;


    GameLogic4x4(){
        gameBoard = new int[4][4];
        for (int r=0; r<4; r++){
            for (int c=0; c<4; c++){
                gameBoard[r][c] = 0;
            }
        }
    }
    public boolean updateGameBoard(int row,int col){
        if (gameBoard[row-1][col-1]==0){
            gameBoard[row-1][col-1]= player;

            if (player==1){
                playerTurn.setText(("Current Player"+" "+name[1]+"'s Turn"));
            }
            else {
                playerTurn.setText(("Current Player"+" "+name[0]+"'s Turn"));
            }

            return true;
        }
        else {
            return false;
        }
    }
    @SuppressLint("SetTextI18n")
    public boolean Winner(){
        boolean win = false;

        //win logic for 4x4
        //horizontal
        for (int r =0; r<4; r++){
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][3] && gameBoard[r][0] != 0)
            {
                win = true;
            }
        }
        //vertical
        for (int c =0; c<4; c++){
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[0][c] == gameBoard[3][c] && gameBoard[0][c] != 0)
            {
                win = true;
            }
        }
        //diagonal
        if (gameBoard[0][0] == gameBoard[1][1]&& gameBoard[0][0] == gameBoard[3][3]&& gameBoard[0][0] != 0)
            {
                win = true;
            }
        if (gameBoard[3][0] == gameBoard[1][1]&& gameBoard[3][0] == gameBoard[0][3]&& gameBoard[3][0] != 0)
            {
                win = true;
            }
        int boardFilled = 0;
        for (int r=0; r<4; r++){
            for (int c=0; c<4; c++){
                if (gameBoard[r][c] != 0){
                    boardFilled +=1;
                }
            }
        }

        if (win){
            playAgainBtn.setVisibility(View.VISIBLE);
            homeBtn.setVisibility(View.VISIBLE);
            playerTurn.setText((name[player-1] + " WON !!!!!!!"));
            return true;
        }
        else if (boardFilled == 20 ){
            playAgainBtn.setVisibility(View.VISIBLE);
            homeBtn.setVisibility(View.VISIBLE);
            playerTurn.setText("Someone Please win!!!!!");
            return true;
        }
        else {
            return false;
        }
    }
    public void resetGame(){
        for (int r=0; r<4; r++){
            for (int c=0; c<4; c++){
                gameBoard[r][c] = 0;
            }
        }
        player = 1;
        playAgainBtn.setVisibility(View.GONE);
        homeBtn.setVisibility(View.GONE);

        playerTurn.setText(("Current Player"+" "+name[0]+"'s Turn"));

    }

    public void setHomeBtn(Button homeBtn) {
        this.homeBtn = homeBtn;
    }
    public void setPlayerTurn(TextView playerTurn){
        this.playerTurn = playerTurn;
    }
    public void setPlayAgainBtn(Button playAgainBtn) {
        this.playAgainBtn = playAgainBtn;
    }
    public void setName(String[] name) {
        this.name = name;
    }

    public int[][] getGameBoard() { return gameBoard; }

    public void setPlayer(int player) { this.player = player; }

    public int getPlayer() { return player; }
}
