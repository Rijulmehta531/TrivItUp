//package com.example.trivitup;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import java.util.ArrayList;
//
//public class QuizActivity extends AppCompatActivity {
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
//}