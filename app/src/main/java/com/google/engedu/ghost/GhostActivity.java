package com.google.engedu.ghost;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends ActionBarActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    TextView tvGhostText, label;
    //SimpleDictionary simpleDictionary;
    FastDictionary simpleDictionary;
    InputStream is = null;
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);


        tvGhostText = (TextView) findViewById(R.id.ghostText);
        label = (TextView) findViewById(R.id.gameStatus);

        try {
            is = getAssets().open("words.txt");
            //for Unit 3
           // simpleDictionary = new SimpleDictionary(is);

            //for Unit 4
            simpleDictionary = new FastDictionary(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        onStart(null);
    }

    public void challengeMethod(View view) {

        String currentText = tvGhostText.getText().toString();

        if (currentText.length() >= 4 && simpleDictionary.isWord(currentText)){
            Toast.makeText(this,"PLAYER WINS!",Toast.LENGTH_SHORT).show();
            label.setText("PLAYER WINS!");
        }
        else {
            String word = simpleDictionary.getAnyWordStartingWith(currentText);

            if (word == null){
                Toast.makeText(this,"PLAYER WINS!",Toast.LENGTH_SHORT).show();
                label.setText("PLAYER WINS!");

            }else {
                Toast.makeText(this,"COMPUTER WINS!",Toast.LENGTH_SHORT).show();
                label.setText("COMPUTER WINS!");
            }
        }
        userTurn = true;
        label.setText(USER_TURN);

    }

    public void restartMethod(View view) {
        onStart(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        int c = event.getUnicodeChar();

        if (c >= 97 && c <= 122) {

            String currentText = tvGhostText.getText().toString();

            String newText = currentText + (char) c;

            tvGhostText.setText(newText);
            label.setText(COMPUTER_TURN);
            computerTurn();
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    private void computerTurn() {

        String currentText = tvGhostText.getText().toString();

        if (currentText.length() >= 4 && simpleDictionary.isWord(currentText)){
            Toast.makeText(this,"COMPUTER WINS!",Toast.LENGTH_SHORT).show();
            label.setText("COMPUTER WINS!");
        }
        else {
            String word = simpleDictionary.getAnyWordStartingWith(currentText);

            if (word == null){
                Toast.makeText(this,"COMPUTER WINS!",Toast.LENGTH_SHORT).show();
                label.setText("COMPUTER WINS!");

            }else {
                String newText = currentText + word.charAt(currentText.length());
                tvGhostText.setText(newText);
            }
        }
        userTurn = true;
        label.setText(USER_TURN);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     *
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }
}

