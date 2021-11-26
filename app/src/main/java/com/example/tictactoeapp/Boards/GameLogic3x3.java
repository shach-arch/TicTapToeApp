package com.example.tictactoeapp.Boards;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic3x3 {
    private int [][] gameBoard;

    private Button playAgainBtn;
    private Button homeBtn;
    private TextView playerTurn;
    private String[] name = {"Player 1","Player 2"};

    private int player = 1;


    GameLogic3x3(){
        gameBoard = new int[3][3];
        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++){
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

        for (int r =0; r<3; r++){
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] &&
                    gameBoard[r][0] != 0) {
                win = true;
            }
        }
        for (int c =0; c<3; c++){
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[0][c] == gameBoard[2][c] &&
                    gameBoard[0][c] != 0) {
                win = true;
            }
        }
        if (gameBoard[0][0] == gameBoard[1][1]&& gameBoard[0][0] == gameBoard[2][2]&&
                gameBoard[0][0] != 0){
                win = true;
            }
        if (gameBoard[2][0] == gameBoard[1][1]&& gameBoard[2][0] == gameBoard[0][2]&&
                gameBoard[2][0] != 0){
            win = true;
        }
        int boardFilled = 0;
        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                if (gameBoard[r][c] != 0){
                    boardFilled +=1;
                }
            }
        }

        if (win){
            playAgainBtn.setVisibility(View.VISIBLE);
            homeBtn.setVisibility(View.VISIBLE);
            playerTurn.setText((name[player-1] + "WON !!!!!!!"));
            return true;
        }
        else if (boardFilled == 9 ){
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
        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++){
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
