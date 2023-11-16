package com.example.trivitup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    // Define the categories
    String[] categories = {"Math", "Science", "General Knowledge","Random"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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