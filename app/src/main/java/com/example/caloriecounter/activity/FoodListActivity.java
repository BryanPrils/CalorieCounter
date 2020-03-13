package com.example.caloriecounter.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.caloriecounter.R;
import com.example.caloriecounter.adapter.FoodAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FoodListActivity extends FragmentActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference foodRef = db.collection("food");
    private FoodAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        //setUpRecyclerView();
    }
}
