package com.example.minigame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView numA, numB, score, operator;
    private Button btnCheck, btnMore, btnLess, btnEqual;
    private int numOperator = 0;
    SharedPreferences sharedPreferences;
    private int scoreInt = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("score", MODE_PRIVATE);

        addControls();
        addEvents();
        generateRandomNumber();
    }

    public void addControls(){
        numA = findViewById(R.id.numA);
        numB = findViewById(R.id.numB);
        score = findViewById(R.id.textViewScore);
        btnCheck = findViewById(R.id.buttonCheck);
        operator = findViewById(R.id.operator);
        btnEqual = findViewById(R.id.buttonEqual);
        btnMore = findViewById(R.id.buttonMore);
        btnLess = findViewById(R.id.buttonLess);

        scoreInt = sharedPreferences.getInt("score", 100);
        score.setText(String.valueOf(scoreInt));
    }

    public void addEvents() {
        btnLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operator.setText("<");
                numOperator = 3;
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operator.setText(">");
                numOperator = 1;
            }
        });
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operator.setText("=");
                numOperator = 2;
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chechResult()){
                    updateScore(10);
                } else {
                    updateScore(-10);
                }
                generateRandomNumber();
                SaveScore();
            }
        });
    }

    public void generateRandomNumber() {
        Random random = new Random();
        int randomNumberA = random.nextInt(20);
        int randomNumberB = random.nextInt(20);

        numA.setText(String.valueOf(randomNumberA));
        numB.setText(String.valueOf(randomNumberB));
    }

    public boolean chechResult(){
        int a = Integer.parseInt(numA.getText().toString().trim());
        int b = Integer.parseInt(numB.getText().toString().trim());

        String operatorValue = operator.getText().toString().trim();

        if (a > b) {
            if (numOperator == 1) {
                return true;
            } else {
                return false;
            }
        }

        if (a == b) {
            if (numOperator == 2) {
                return true;
            } else {
                return false;
            }
        }

        if (a < b) {
            if (numOperator == 3) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void updateScore(int scoreIntput){
        scoreInt = Integer.parseInt(score.getText().toString().trim());
        scoreInt += scoreIntput;
        score.setText(String.valueOf(scoreInt));
    }

    private void SaveScore() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("score", scoreInt);
        editor.commit();
    }
}