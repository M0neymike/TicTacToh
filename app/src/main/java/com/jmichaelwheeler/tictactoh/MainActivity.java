package com.jmichaelwheeler.tictactoh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3];

    private boolean usturn = true;

    private int roundCount;

    private int usPoints;
    private int txPoints;

    private TextView textViewus;
    private TextView textViewtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewus = findViewById(R.id.text_view_us);
        textViewtx = findViewById(R.id.text_view_tx);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (usturn) {
            ((Button) v).setText("US");
            ((Button) v).setBackgroundResource(R.drawable.us_flag);
        } else {
            ((Button) v).setText("TX");
            ((Button) v).setBackgroundResource(R.drawable.texas_flag);
        }

        roundCount++;

        if (checkForWin()) {
            if (usturn){
                usWins();
            } else {
                txWins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            usturn = !usturn;
        }

    }

    private boolean checkForWin(){
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void usWins() {
        usPoints++;
        Toast.makeText(this, "US Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void txWins() {
        txPoints++;
        Toast.makeText(this, "TX Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewus.setText("United States:" + usPoints);
        textViewtx.setText("Texas:" + txPoints);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackgroundResource(R.drawable.gray_button);
            }
        }

        roundCount = 0;
        usturn = true;
    }

    private void resetGame() {
        usPoints = 0;
        txPoints = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("usPoints", usPoints);
        outState.putInt("txPoints", txPoints);
        outState.putBoolean("usturn", usturn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        usPoints = savedInstanceState.getInt("usPoints");
        txPoints = savedInstanceState.getInt("txPoints");
        usturn = savedInstanceState.getBoolean("usturn");
    }
}
