package com.example.caloriecounter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.ItemClickListener;
import com.example.caloriecounter.R;
import com.example.caloriecounter.adapter.FoodAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.Food;
import model.UserDiary;

public class FoodListActivity extends FragmentActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference foodRef = db.collection("food");
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private FoodAdapter adapter;
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);


        //searchText = findViewById(R.id.edit_text_search);

        setUpRecyclerView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_food) {
            startActivityForResult(new Intent(FoodListActivity.this, NewFoodActivity.class), 1);

        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpRecyclerView() {

        Query query = foodRef.orderBy("name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Food> options = new FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(query, Food.class)
                .build();


        adapter = new FoodAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
                Food food = documentSnapshot.toObject(Food.class);
                String id = documentSnapshot.getId();

                Intent intent = new Intent(FoodListActivity.this, MainActivity.class);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date today = Calendar.getInstance().getTime();


                Toast.makeText(FoodListActivity.this, "Position: " +
                        position + " ID: " + id, Toast.LENGTH_SHORT).show();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                CollectionReference userDiaryRef = FirebaseFirestore.getInstance()
                        .collection("userDiary");
                userDiaryRef.add(new UserDiary(user.getEmail(), dateFormat.format(today), food.getName(), food.getCalories()));
                MainActivity.diaries.clear();
                finish();
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
