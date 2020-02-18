package edu.quinnipiac.ser210;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * TicTacToe class implements the interface
 * @author relkharboutly
 * Author: Julia Wilkinson
 * @date 1/5/2017
 * date: 1/31/2020
 */
public class TicTacToe extends Activity implements ITicTacToe {

    // The game board and the game status
    private static final int ROWS = 3, COLS = 3; // number of rows and columns
    private int[][] board = new int[ROWS][COLS]; // game board in 2D array
    private int rounds; // the number of rounds
    private int symbol;
    private int[] buttonNums = {R.id.one,R.id.two,R.id.three,R.id.four,R.id.five,R.id.six,R.id.seven,R.id.eight,R.id.nine};
    private ArrayList<Button> buttons; // the buttons
    String name; //name of player
    TextView player; //the player


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        TextView nameView = (TextView) findViewById(R.id.name);
        nameView.setText("Hello " + name );
        player = (TextView) findViewById(R.id.name);
        buttons = new ArrayList<Button>();
        for(int id : buttonNums) {
            Button button = (Button) findViewById(id);
            buttons.add(button);
        }
    }

    public void onClickMove(View view){
        System.out.println("onclickmove");
        this.setMove(1,buttons.indexOf(findViewById(view.getId())));

        this.setMove(2, this.getComputerMove());


        display();
        if(checkForWinner() == TIE){
            showDialog(this,"Tie","It's a tie!");
        } else if(checkForWinner() == CROSS_WON){
            showDialog(this,"Winner","You won!");
        } else if(checkForWinner() == NOUGHT_WON){
            showDialog(this,"Loser","You lose!");
        }
    }
    public void showDialog(Activity activity,String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null) builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                clearBoard();
                findViewById(R.id.one).setBackgroundResource(0);
                findViewById(R.id.two).setBackgroundResource(0);
                findViewById(R.id.three).setBackgroundResource(0);
                findViewById(R.id.four).setBackgroundResource(0);
                findViewById(R.id.five).setBackgroundResource(0);
                findViewById(R.id.six).setBackgroundResource(0);
                findViewById(R.id.seven).setBackgroundResource(0);
                findViewById(R.id.eight).setBackgroundResource(0);
                findViewById(R.id.nine).setBackgroundResource(0);

            }
        });
        builder.setNegativeButton("Main Menu", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                TicTacToe.this.finish();
            }

        });
        builder.show();
    }
    public void display(){
        System.out.println("display");
        int z = 0;
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                switch(board[i][j]){
                    case 0:
                        z++;
                        break;
                    case 1:
                        buttons.get(z).setBackgroundResource(R.drawable.x);
                        z++;
                        break;
                    case 2:
                        buttons.get(z).setBackgroundResource(R.drawable.o);
                        z++;
                        player.setText("Player is: " + name);
                        break;
                }
            }
        }
    }
    /**
     * clear board and set current player
     */
    public TicTacToe() {
        rounds = 0;
    }

    @Override
    public void clearBoard() {
        rounds = 0;
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }
    //Set the move of the player, or the computer.
    @Override
    public void setMove(int player, int location) {
        System.out.println("csetmove");
        board[(int)(location/3)][location%3] = player;
        //Add a round each move.
        rounds++;

        switch (player) {
            case 0:
                symbol = NOUGHT;
                break;
            case 1:
                symbol = CROSS;
                break;
            default:
                symbol = EMPTY;
                break;
        }
    }

    //Compute the computer's next move.
    @Override
    public int getComputerMove() {

        int location = -1;

        if (location == -1) {
            this.tryToBlock(1);
            location = this.tryToBlock(2);
        }
        if (location == -1) {
            location = this.tryToBlockDiagonal();
        }


        return location;
    }
    //Check if there is a winner after each round.
    @Override
    public int checkForWinner() {
        System.out.println("checkforwinner");

        //Check if the top row is the same.
        if((board[0][0] == board[0][1]) && (board[0][1] == board[0][2]))
        {
            //check which player won(cross)
            if(board[0][0] == CROSS)
            {
                return CROSS_WON;
            }
            //check which player won (nought)
            else if(board[0][0] == NOUGHT)
            {
                return NOUGHT_WON;
            }
        }
        //Check the middle row
        if((board[1][0] == board[1][1]) && (board[1][1] == board[1][2]))
        {
            if(board[1][0] == CROSS)
            {
               return CROSS_WON;
            }
            else if(board[1][0] == NOUGHT)
            {
                return NOUGHT_WON;
            }
        }
        //Check bottom row for winner
        if((board[2][0] == board[2][1]) && (board[2][1] == board[2][2]))
        {
            if(board[2][0] == CROSS)
            {
                return CROSS_WON;
            }
            else if(board[2][0] == NOUGHT)
            {
                return NOUGHT_WON;
            }
        }
        //Check left column for winner.
        if((board[0][0] == board[1][0]) && (board[1][0] == board[2][0]))
        {
            if(board[0][0] == CROSS)
            {
                return CROSS_WON;
            }
            else if(board[0][0] == NOUGHT)
            {
                return NOUGHT_WON;
            }
        }
        //Check middle column for winner.
        if((board[0][1] == board[1][1]) && (board[1][1] == board[2][1]))
        {
            if(board[0][1] == CROSS)
            {
               return CROSS_WON;
            }
            else if(board[0][1] == NOUGHT)
            {
                return NOUGHT_WON;
            }
        }
        //Check right column for winner.
        if((board[0][2] == board[1][2]) && (board[1][2] == board[2][2]))
        {
            if(board[0][2] == CROSS)
            {
                return CROSS_WON;
            }
            else if(board[0][2] == NOUGHT)
            {
                return NOUGHT_WON;
            }
        }
        //Check the diagonals for a winner.
        if((board[0][0] == board[1][1]) && (board[1][1] == board[2][2]))
        {
            if(board[0][0] == CROSS)
            {
                return CROSS_WON;
            }
            else if(board[0][0] == NOUGHT)
            {
                return NOUGHT_WON;
            }
        }
        //Check diagonals for a winner.
        if((board[0][2] == board[1][1]) && (board[1][1] == board[2][0]))
        {
            if(board[0][2] == CROSS)
            {
                return CROSS_WON;
            }
            else if(board[0][2] == NOUGHT)
            {
               return NOUGHT_WON;
            }
        }
        //Check if the board has been filled or there is one empty but no winners.
        if ((rounds >= 8)) {
            //its a tie
            return TIE;
            //if there are no more moves its a tie.
        } else if ((rounds == 7)) {
            return TIE;
        }
        return PLAYING;
    }
    private int tryToBlock(int direction) {
        System.out.println("trytoblock");
        int emptyRow = -1;
        int emptyCol = -1;
        for (int i = 0; i < ROWS; i++) {
            int countfull = 0;
            for (int j = 0; j < COLS; j++) {
                if (direction == 1) {
                    if (checkCell(i, j, symbol, board)) {
                        countfull++;
                    }
                    if (checkCell(i, j, EMPTY, board)) {
                        emptyRow = i;
                        emptyCol = j;
                    }
                } else {
                    if (checkCell(j, i, symbol, board)) {
                        countfull++;
                    }
                    if (checkCell(j, i, EMPTY, board)) {
                        emptyRow = j;
                        emptyCol = i;
                    }
                }
                if (countfull == 2) {
                    break;
                }
            }
        }
        return this.getLocationFromRowCol(emptyRow, emptyCol);
    }

    private int tryToBlockDiagonal() {
        System.out.println("trytobDiag");
        int emptyRow = -1;
        int emptyCol = -1;
        int diagCount = 0;
        for (int i = 0; i <= ROWS; i++) {
            if (board[i][i] == symbol) {
                diagCount++;
            }
            if (board[i][i] == EMPTY) {
                emptyRow = i;
                emptyCol = i;
            }
        }
        if (diagCount == 2) {
            return this.getLocationFromRowCol(emptyRow, emptyCol);
        } else {
            return -1;
        }
    }

    private int getLocationFromRowCol(int row, int col){
        System.out.println("getlocation");
        if(row != -1 && col != -1) {
            return ((row * COLS) + col - COLS - 1);
        } else {
            return -1;
        }
    }

    private boolean checkCell(int row,int col, int symbol, int[][] board){
        System.out.println("checkcell");
        return (board[row][col] == symbol);
    }

    /**
     *  Print the game board
     */
    public  void printBoard() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                printCell(board[row][col]); // print each of the cells
                if (col != COLS - 1) {
                    System.out.print("|");   // print vertical partition
                }
            }
            System.out.println();
            if (row != ROWS - 1) {
                System.out.println("-----------"); // print horizontal partition
            }
        }
        System.out.println();
    }

    /**
     * Print a cell with the specified "content"
     * @param content either CROSS, NOUGHT or EMPTY
     */
    public void printCell(int content) {
        switch (content) {
            case EMPTY:  System.out.print("   "); break;
            case NOUGHT: System.out.print(" O "); break;
            case CROSS:  System.out.print(" X "); break;
        }
    }

}
