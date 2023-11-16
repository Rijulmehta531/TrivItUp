package com.example.trivitup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class SubCategoryActivity extends AppCompatActivity {
    // Define the subcategories
    Map<String, String[]> subCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        // Get the layout
        LinearLayout layout = (LinearLayout) findViewById(R.id.button_subcat);

        // Initialize and populate the subcategories
        subCategories = new HashMap<>();
        subCategories.put("Math", new String[]{"Algebra", "Calculus", "Geometry", "Statistics", "Trigonometry"});
        subCategories.put("Science", new String[]{"Physics", "Chemistry", "Biology", "Astronomy", "Geology"});
        subCategories.put("General Knowledge", new String[]{"History", "Geography", "Art", "Literature", "Sports"});
        subCategories.put("Random", new String[]{"Movies", "Music", "Pop Culture", "Technology", "Food"});

        // Get the category from the intent
        String category = getIntent().getStringExtra("CATEGORY");

        // Get the subcategories for this category
        String[] subCategoriesForCategory = subCategories.get(category);

        for (String subCategory : subCategoriesForCategory) {
            Button button = new Button(this);
            button.setText(subCategory);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startQuiz(subCategory);
                }
            });
            // Add the button to the layout
            layout.addView(button);
        }
    }
    // Method to start the quiz
    void startQuiz(String subCategory) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("SUB_CATEGORY", subCategory);
        startActivity(intent);
    }
}