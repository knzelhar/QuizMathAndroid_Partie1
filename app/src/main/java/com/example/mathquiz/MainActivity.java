package com.example.mathquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtNum1, txtNum2, txtResult;
    Button btnPlus, btnMinus, btnMultiply, btnGenerate;
    Random random;
    int num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        txtNum1 = findViewById(R.id.txtNum1);
        txtNum2 = findViewById(R.id.txtNum2);
        txtResult = findViewById(R.id.txtResult);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnGenerate = findViewById(R.id.btnGenerate);

        random = new Random();

        // Générer des nombres au démarrage
        generateNumbers();

        // Événements des boutons
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate("+");
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate("-");
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate("×");
            }
        });

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateNumbers();
                txtResult.setText("Résultat :");
            }
        });
    }

    // Générer deux nombres aléatoires entre 111 et 999
    private void generateNumbers() {
        num1 = random.nextInt(889) + 111; // 111 à 999
        num2 = random.nextInt(889) + 111;

        txtNum1.setText(String.valueOf(num1));
        txtNum2.setText(String.valueOf(num2));
    }

    // Calculer selon l'opération choisie
    private void calculate(String operation) {
        int result = 0;

        switch (operation) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "×":
                result = num1 * num2;
                break;
        }

        txtResult.setText("Résultat : " + result);
    }
}