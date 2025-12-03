package com.example.mathquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtNum1, txtNum2, txtOperation, txtResult;
    EditText editAnswer;
    Button btnPlus, btnMinus, btnMultiply, btnGenerate, btnCheck;
    Random random;
    int num1, num2;
    String currentOperation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtNum1 = findViewById(R.id.txtNum1);
        txtNum2 = findViewById(R.id.txtNum2);
        txtOperation = findViewById(R.id.txtOperation);
        txtResult = findViewById(R.id.txtResult);
        editAnswer = findViewById(R.id.editAnswer);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnCheck = findViewById(R.id.btnCheck);

        random = new Random();


        generateNumbers();


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOperation("+");
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOperation("-");
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOperation("×");
            }
        });


        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });


        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateNumbers();
                resetQuiz();
            }
        });
    }


    private void generateNumbers() {
        num1 = random.nextInt(889) + 111; // 111 à 999
        num2 = random.nextInt(889) + 111;

        txtNum1.setText(String.valueOf(num1));
        txtNum2.setText(String.valueOf(num2));
    }


    private void selectOperation(String operation) {
        currentOperation = operation;
        txtOperation.setText(operation);
        txtResult.setText("");
        editAnswer.setText("");
    }


    private void checkAnswer() {

        if (currentOperation.isEmpty()) {
            txtResult.setText(R.string.choose_operation);
            txtResult.setTextColor(Color.parseColor("#FF9800"));
            return;
        }


        String userAnswerStr = editAnswer.getText().toString();
        if (userAnswerStr.isEmpty()) {
            txtResult.setText(R.string.empty_answer);
            txtResult.setTextColor(Color.parseColor("#FF9800"));
            return;
        }


        int correctResult = 0;
        switch (currentOperation) {
            case "+":
                correctResult = num1 + num2;
                break;
            case "-":
                correctResult = num1 - num2;
                break;
            case "×":
                correctResult = num1 * num2;
                break;
        }


        int userAnswer = Integer.parseInt(userAnswerStr);

        if (userAnswer == correctResult) {
            txtResult.setText(R.string.correct);
            txtResult.setTextColor(Color.parseColor("#4CAF50")); // Vert
        } else {
            txtResult.setText(getString(R.string.incorrect, correctResult));
            txtResult.setTextColor(Color.parseColor("#F44336")); // Rouge
        }
    }


    private void resetQuiz() {
        currentOperation = "";
        txtOperation.setText("+");
        editAnswer.setText("");
        txtResult.setText("");
    }
}