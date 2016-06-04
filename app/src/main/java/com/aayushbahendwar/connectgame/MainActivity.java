package com.aayushbahendwar.connectgame;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean oTurn = true; //when true, yellow's turn and when false, red's turn

    String[] playerPositions = new String [9]; //stores the player positions

    boolean winStatus = false;

    public void playTurn(View view){

        ImageView playPiece = (ImageView) view;

        // if condition: only play when spot is empty and no one has won
        if(playerPositions[Integer.parseInt(playPiece.getTag().toString())] == null && winStatus == false) {

            playPiece.setTranslationY(-1000f); //setting initial position

            if (oTurn) {

                //player O's turn

                playPiece.setImageResource(R.drawable.opic); //setting image in ImageView

                playerPositions[Integer.parseInt(playPiece.getTag().toString())] = "o";

                oTurn = false;

            }

            else if (oTurn == false) {

                //player X's turn

                playPiece.setImageResource(R.drawable.xpic); //setting image in ImageView

                playerPositions[Integer.parseInt(playPiece.getTag().toString())] = "x";

                oTurn = true;

            }

            playPiece.animate().translationYBy(1000f).setDuration(200); //animation onto screen
        }

        else{

            if (winStatus){
                Toast.makeText(getApplicationContext(), "Game has been won.", Toast.LENGTH_SHORT).show();
            }
            else {
                //message is displayed when played position is tapped.
                Toast.makeText(getApplicationContext(), "Position already played.", Toast.LENGTH_SHORT).show();
            }
        }

        checkWin(); //checking if any of the players has won.
    }

    public void checkWin(){

        //checking horizontal combinations
        for (int i = 0; i <= 6; i+=3){ //adds 3 to the positions every iteration to move to the next row

            if(playerPositions[0+i] == playerPositions[1+i] && playerPositions[1+i] == playerPositions[2+i]
                    && playerPositions[0+i] != null){
                Toast.makeText(getApplicationContext(), "Winner is: " + playerPositions[0+i] + " !", Toast.LENGTH_LONG).show();
                winStatus = true;
            }
        }

        //checking vertical combinations
        for (int j = 0; j < 3; j+=1){ //adds 1 to the positions every iteration to move to the next column

            if(playerPositions[0+j] == playerPositions[3+j] && playerPositions[3+j] == playerPositions[6+j]
                    && playerPositions[0+j] != null){
                Toast.makeText(getApplicationContext(), "Winner is: " + playerPositions[0+j] + " !", Toast.LENGTH_LONG).show();
                winStatus = true;
            }
        }

        //checking diagonal, positions: 0 4 8
        if( playerPositions[0] == playerPositions[4] && playerPositions[4] == playerPositions[8] && playerPositions[0] != null){
            Toast.makeText(getApplicationContext(), "Winner is: " + playerPositions[0] + " !", Toast.LENGTH_LONG).show();
            winStatus = true;
        }

        //checking diagonal, positions: 2 4 6
        if( playerPositions[2] == playerPositions[4] && playerPositions[4] == playerPositions[6] && playerPositions[2] != null){
            Toast.makeText(getApplicationContext(), "Winner is: " + playerPositions[2] + " !", Toast.LENGTH_LONG).show();
            winStatus = true;
        }

        //give player the option to restart after winning

        if(winStatus){
            //when a player wins, a restart panel with a win message glides down onto the screen
            TextView messageText = (TextView) findViewById(R.id.messageText);
            messageText.setText("Win!");
            showPanel();
        }

        //checking for draw
        if(winStatus == false){
            boolean isFull = true;
            for(int i = 0; i < 9; i++){
               if(playerPositions[i] == null){
                   isFull = false; //set boolean to false if any position is not filled or null.
               }
            }
            if(isFull){
                TextView messageText = (TextView) findViewById(R.id.messageText);
                messageText.setText("Draw.");
                showPanel();
                Toast.makeText(getApplicationContext(), "Draw.", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void showPanel(){ //moves panel onto the screen
        LinearLayout winLayout = (LinearLayout) findViewById(R.id.winLayout);
        winLayout.setTranslationY(-1000f);
        winLayout.setVisibility(View.VISIBLE);
        winLayout.animate().translationYBy(1000f).setDuration(300);
    }

    public void restart(View view){

        LinearLayout winLayout = (LinearLayout) findViewById(R.id.winLayout);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        winLayout.setVisibility(View.INVISIBLE); // making win panel invisible

        winStatus = false;

        //reset player positions array
        for (int i = 0; i < 9; i++){
            playerPositions[i] = null;
        }

        //resetting image in the positions
        for (int j = 0; j < 9; j++){
            ((ImageView) gridLayout.getChildAt(j)).setImageResource(0);
            // putting in 0 reset the image to nothing
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
