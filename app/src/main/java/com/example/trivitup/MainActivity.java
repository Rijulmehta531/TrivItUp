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

    private Button logoutButton;
    private TextView welcomeTextView;
    private Button playIndividuallyButton;
    private Button selectedCategoryButton;

    // Define the categories
    String[] categories = {"Math", "Science", "General Knowledge", "Random"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeTextView = findViewById(R.id.WelcomeTV);
        playIndividuallyButton = findViewById(R.id.playIndividuallyButton);
        selectedCategoryButton = null;  // To keep track of the selected category

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

        // Logout button
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this, AuthenticationActivity.class);
                startActivity(i);
            }
        });

// Loop through the categories and create buttons with radio button styling for each
        LinearLayout layout = findViewById(R.id.button_cat);
        for (String category : categories) {
            Button button = new Button(this);
            button.setText(category);
            button.setId(View.generateViewId());
            button.setBackground(getDrawable(R.drawable.radio_button_background));  // Use a drawable for the radio button style

            // Set margins to create spacing between buttons
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.button_margin_bottom)); // Adjust margin as needed

            button.setLayoutParams(params);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCategoryButtonClicked((Button) view);
                }
            });
            layout.addView(button);
        }


        // Play Individually button
        playIndividuallyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedCategoryButton != null) {
                    openSubCategories(selectedCategoryButton.getText().toString());
                }
            }
        });
    }

    // Method to handle button clicks for category selection
    void onCategoryButtonClicked(Button clickedButton) {
        if (selectedCategoryButton != null) {
            // Reset the background and text color of the previously selected button
            selectedCategoryButton.setBackground(getDrawable(R.drawable.radio_button_background));
            selectedCategoryButton.setTextColor(getColor(R.color.default_text_color)); // Change to your default text color
        }

        // Set the background and text color of the clicked button to indicate selection
        clickedButton.setBackground(getDrawable(R.drawable.radio_button_selected_background));
        clickedButton.setTextColor(getColor(R.color.selected_text_color)); // Change to your selected text color
        selectedCategoryButton = clickedButton;
    }


    // Method to open the subcategories
    void openSubCategories(String category) {
        Intent intent = new Intent(this, SubCategoryActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }
}
