package com.example.trivitup;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class QuizActivity extends AppCompatActivity {
    //    // Define the Firebase database reference
//    DatabaseReference mDatabase;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_quiz);
//
//        // Get the subcategory from the intent
//        String subCategory = getIntent().getStringExtra("SUB_CATEGORY");
//        // Initialize the Firebase database reference
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("Quizzes").child(subCategory);
//
//        // Fetch the questions from Firebase
//        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get the questions
//                List<Question> questions = new ArrayList<>();
//                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
//                    Question question = questionSnapshot.getValue(Question.class);
//                    questions.add(question);
//                }
//
//                // Display the questions one by one
//                for (int i = 0; i < 10 && i < questions.size(); i++) {
//                    displayQuestion(questions.get(i));
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle possible errors.
//            }
//
//        });
//    }
//
//    // Method to display a question
//    void displayQuestion(Question question) {
//        // Display the question
//    }
    // ...

    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button submitButton;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private final long countdownInterval = 1000; // 1 second
    private final long totalQuizTimeMillis = 30000; // 30 seconds

    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private TextView questionNumberTextView;
    private TextView subCategoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        timerTextView = findViewById(R.id.timerTextView);
        startCountdownTimer();

        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        subCategoryTextView = findViewById(R.id.subCategoryTextView);

        String subCategory = getIntent().getStringExtra("SUB_CATEGORY");
        questions = getIntent().getParcelableArrayListExtra("QUESTIONS");

        if (questions != null && !questions.isEmpty()) {
            displayQuestion(questions.get(currentQuestionIndex));
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

        displayQuestionInfo();
    }

    private void startCountdownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(totalQuizTimeMillis, countdownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                timerTextView.setText("Time Remaining: " + secondsRemaining + "s");
            }

            @Override
            public void onFinish() {
                showTimeoutDialog();
            }
        };

        countDownTimer.start();
    }

    private void showTimeoutDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.timeout_dialog);

        TextView timeoutMessageTextView = dialog.findViewById(R.id.timeoutMessageTextView);
        timeoutMessageTextView.setText("Time's up! Quiz completed.");

        Button goBackButton = dialog.findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void displayQuestionInfo() {
        int questionNumber = currentQuestionIndex + 1;  // Adjust to 1-based index
        questionNumberTextView.setText("Question " + questionNumber);

        String subCategory = getIntent().getStringExtra("SUB_CATEGORY");
        subCategoryTextView.setText("Subcategory: " + subCategory);
    }

    private void displayQuestion(Question question) {
        questionTextView.setText(question.getQuestionText());

        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            RadioButton radioButton = (RadioButton) optionsRadioGroup.getChildAt(i);
            radioButton.setText(options[i]);
        }

        optionsRadioGroup.clearCheck();
    }

    private void checkAnswer() {
        int selectedRadioButtonId = optionsRadioGroup.getCheckedRadioButtonId();

        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            int selectedOptionIndex = optionsRadioGroup.indexOfChild(selectedRadioButton);

            Question currentQuestion = questions.get(currentQuestionIndex);

            if (selectedOptionIndex == currentQuestion.getCorrectOptionIndex()) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                correctAnswers++;
            } else {
                Toast.makeText(this, "Wrong! Correct answer is Option " + (currentQuestion.getCorrectOptionIndex() + 1), Toast.LENGTH_SHORT).show();
            }

            currentQuestionIndex++;
            displayQuestionInfo();
            if (currentQuestionIndex < questions.size()) {
                displayQuestion(questions.get(currentQuestionIndex));
                startCountdownTimer();
            } else {
                Toast.makeText(this, "Quiz completed!", Toast.LENGTH_SHORT).show();
            }

            if (currentQuestionIndex == questions.size()) {
                showQuizResultDialog(correctAnswers);
            }
        } else {
            Toast.makeText(this, "Please select an answer.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showQuizResultDialog(int correctAnswers) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.quiz_result_dialog);

        TextView resultTextView = dialog.findViewById(R.id.resultTextView);
        resultTextView.setText("Total Correct Answers: " + correctAnswers);

        Button goBackButton = dialog.findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();
    }
}