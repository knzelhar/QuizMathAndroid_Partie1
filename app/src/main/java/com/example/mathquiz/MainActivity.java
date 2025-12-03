package com.example.mathquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtNum1, txtNum2, txtOperation, txtResult, txtScore, txtPercentage;
    EditText editAnswer;
    Button btnPlus, btnMinus, btnMultiply, btnGenerate, btnCheck, btnResetScore;
    Random random;
    int num1, num2;
    String currentOperation = "";

    // NOUVEAU : Variables pour le score
    int correctAnswers = 0;
    int totalAttempts = 0;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        txtNum1 = findViewById(R.id.txtNum1);
        txtNum2 = findViewById(R.id.txtNum2);
        txtOperation = findViewById(R.id.txtOperation);
        txtResult = findViewById(R.id.txtResult);
        txtScore = findViewById(R.id.txtScore);
        txtPercentage = findViewById(R.id.txtPercentage);
        editAnswer = findViewById(R.id.editAnswer);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnCheck = findViewById(R.id.btnCheck);
        btnResetScore = findViewById(R.id.btnResetScore);

        random = new Random();

        // NOUVEAU : Initialiser SharedPreferences pour sauvegarder le score
        sharedPreferences = getSharedPreferences("MathQuizPrefs", MODE_PRIVATE);
        loadScore();

        // Générer des nombres au démarrage
        generateNumbers();

        // Événements des boutons d'opération
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

        // Bouton Vérifier
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        // Bouton Générer
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateNumbers();
                resetQuiz();
            }
        });

        // NOUVEAU : Bouton réinitialiser score
        btnResetScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetScore();
            }
        });
    }

    // Générer deux nombres aléatoires entre 111 et 999
    private void generateNumbers() {
        num1 = random.nextInt(889) + 111;
        num2 = random.nextInt(889) + 111;

        txtNum1.setText(String.valueOf(num1));
        txtNum2.setText(String.valueOf(num2));
    }

    // Sélectionner une opération
    private void selectOperation(String operation) {
        currentOperation = operation;
        txtOperation.setText(operation);
        txtResult.setText("");
        editAnswer.setText("");
    }

    // Vérifier la réponse de l'utilisateur
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

        // Calculer le bon résultat
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

        // Comparer avec la réponse de l'utilisateur
        int userAnswer = Integer.parseInt(userAnswerStr);

        // NOUVEAU : Incrémenter le total des tentatives
        totalAttempts++;

        if (userAnswer == correctResult) {
            correctAnswers++; // NOUVEAU : Incrémenter les bonnes réponses
            txtResult.setText(R.string.correct);
            txtResult.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            txtResult.setText(getString(R.string.incorrect, correctResult));
            txtResult.setTextColor(Color.parseColor("#F44336"));
        }

        // NOUVEAU : Mettre à jour l'affichage du score
        updateScoreDisplay();
        saveScore();
    }

    // Réinitialiser le quiz
    private void resetQuiz() {
        currentOperation = "";
        txtOperation.setText("+");
        editAnswer.setText("");
        txtResult.setText("");
    }

    // NOUVEAU : Mettre à jour l'affichage du score
    private void updateScoreDisplay() {
        txtScore.setText(getString(R.string.score_label, correctAnswers, totalAttempts));

        int percentage = 0;
        if (totalAttempts > 0) {
            percentage = (correctAnswers * 100) / totalAttempts;
        }
        txtPercentage.setText(getString(R.string.percentage_label, percentage));

        // Changer la couleur selon le taux de réussite
        if (percentage >= 80) {
            txtPercentage.setTextColor(Color.parseColor("#4CAF50")); // Vert
        } else if (percentage >= 50) {
            txtPercentage.setTextColor(Color.parseColor("#FF9800")); // Orange
        } else {
            txtPercentage.setTextColor(Color.parseColor("#F44336")); // Rouge
        }
    }

    // NOUVEAU : Sauvegarder le score
    private void saveScore() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("correctAnswers", correctAnswers);
        editor.putInt("totalAttempts", totalAttempts);
        editor.apply();
    }

    // NOUVEAU : Charger le score sauvegardé
    private void loadScore() {
        correctAnswers = sharedPreferences.getInt("correctAnswers", 0);
        totalAttempts = sharedPreferences.getInt("totalAttempts", 0);
        updateScoreDisplay();
    }

    // NOUVEAU : Réinitialiser le score
    private void resetScore() {
        correctAnswers = 0;
        totalAttempts = 0;
        updateScoreDisplay();
        saveScore();
        txtResult.setText("Score réinitialisé !");
        txtResult.setTextColor(Color.parseColor("#2196F3"));
    }
}