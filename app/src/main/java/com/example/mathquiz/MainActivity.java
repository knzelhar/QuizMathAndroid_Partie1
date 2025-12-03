package com.example.mathquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText num1, num2;
    Button btnPlus, btnMinus, btnMultiply, btnGenerate;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnGenerate = findViewById(R.id.btnGenerate);
        txtResult = findViewById(R.id.txtResult);

        // Bouton plus
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate("+");
            }
        });

        // Bouton moins
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate("-");
            }
        });

        // Bouton multiplication
        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate("*");
            }
        });

        // Bouton générer un calcul aléatoire
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomOperation();
            }
        });
    }

    // Fonction de calcul
    private void calculate(String operation) {
        try {
            int a = Integer.parseInt(num1.getText().toString());
            int b = Integer.parseInt(num2.getText().toString());
            int result = 0;

            switch (operation) {
                case "+":
                    result = a + b;
                    break;

                case "-":
                    result = a - b;
                    break;

                case "*":
                    result = a * b;
                    break;
            }

            txtResult.setText("Résultat : " + result);

        } catch (Exception e) {
            txtResult.setText("Veuillez entrer des nombres valides !");
        }
    }

    // Fonction pour générer un calcul aléatoire
    private void generateRandomOperation() {
        int a = (int) (Math.random() * 10);
        int b = (int) (Math.random() * 10);
        int op = (int) (Math.random() * 3); // 0:+, 1:-, 2:*

        String symbol = "";
        int result = 0;

        if (op == 0) {
            symbol = "+";
            result = a + b;
        } else if (op == 1) {
            symbol = "-";
            result = a - b;
        } else {
            symbol = "*";
            result = a * b;
        }

        txtResult.setText(a + " " + symbol + " " + b + " = " + result);
    }
}
