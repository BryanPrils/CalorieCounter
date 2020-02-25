package com.example.caloriecounter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.R;
import com.example.caloriecounter.adapter.MyAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import model.UserDiary;

public class MainActivity extends AppCompatActivity {
    public static TextView textViewTotalCal;
    public static List<UserDiary> diaries;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userDiaryRef = db.collection("userDiary");
    private MyAdapter mAdapter;
    private int totalcal;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTotalCal = findViewById(R.id.tv_total_cal);
        diaries = new LinkedList<>();

        setUpRecyclerView();


    }


    private void setUpRecyclerView() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
        Date today = Calendar.getInstance().getTime();
        Query query = userDiaryRef.whereEqualTo("date", dateFormat.format(today));

        FirestoreRecyclerOptions<UserDiary> options = new FirestoreRecyclerOptions.Builder<UserDiary>()
                .setQuery(query, UserDiary.class)
                .build();

        mAdapter = new MyAdapter(options);

        recyclerView = findViewById(R.id.recycler_view_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.products_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_food) {
            startActivityForResult(new Intent(MainActivity.this, FoodListActivity.class), 1);

        }
        if (item.getItemId() == R.id.logoff) {
            startActivityForResult(new Intent(MainActivity.this, AccountLoginActivity.class), 1);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }


}
