package com.example.trivitup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private TextView welcomeTextView;
    // Define the categories
    String[] categories = {"Math", "Science", "General Knowledge", "Random"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeTextView = findViewById(R.id.WelcomeTV);

        // Get the current user from FirebaseAuth and Check if the user is authenticated
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String displayName = user.getDisplayName();

            if (displayName != null && !displayName.isEmpty()) {
                welcomeTextView.setText("Welcome, " + displayName + "!");
            } else {
                welcomeTextView.setText("Welcome, User!");
            }
        }

        //Logout button
        btn = findViewById(R.id.logoutButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this, AuthenticationActivity.class);
                startActivity(i);
            }
        });

        // Get the layout
        LinearLayout layout = (LinearLayout) findViewById(R.id.button_cat);
        // Loop through the categories and create buttons for each
        for (String category : categories) {
            Button button = new Button(this);
            button.setText(category);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openSubCategories(category);
                }
            });
            // Add the button to the layout
            layout.addView(button);
        }
    }

    // Method to open the subcategories
    void openSubCategories(String category) {
        Intent intent = new Intent(this, SubCategoryActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }
}