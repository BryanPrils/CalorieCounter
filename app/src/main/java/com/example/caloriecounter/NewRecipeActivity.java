package com.example.caloriecounter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class NewRecipeActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextIngredients;
    private TextView textViewIngredients;
    private ArrayList<String> ingredients = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("add Recipe");

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextIngredients = findViewById(R.id.edit_text_ingredients);
        textViewIngredients = findViewById(R.id.tv_ingredients);

        Button addIngredients = findViewById(R.id.button_add_ingredients);
        addIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredients.add(editTextIngredients.getText().toString());
                textViewIngredients.append("\n " + editTextIngredients.getText().toString());
                editTextIngredients.setText("");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_recipe_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveRecipe();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveRecipe() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();


        //ingredients.add(textViewIngredients.getText().toString());

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert text", Toast.LENGTH_SHORT).show();
            return;
        }
        CollectionReference recipeBookRef = FirebaseFirestore.getInstance()
                .collection("RecipeBook");
        recipeBookRef.add(new Recipe(title, description, ingredients));
        Toast.makeText(this, "Recipe added", Toast.LENGTH_SHORT).show();
        finish();

    }
}
