package com.example.caloriecounter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.R;
import com.example.caloriecounter.adapter.MyAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference userDiaryRef = db.collection("userDiary");
    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;

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
        Query query = userDiaryRef.whereEqualTo("date", dateFormat.format(today))
                .whereEqualTo("email", user.getEmail());

        FirestoreRecyclerOptions<UserDiary> options = new FirestoreRecyclerOptions.Builder<UserDiary>()
                .setQuery(query, UserDiary.class)
                .build();

        mAdapter = new MyAdapter(options);

        mRecyclerView = findViewById(R.id.recycler_view_main);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.deleteItem(viewHolder.getAdapterPosition());
                diaries.remove(viewHolder.getAdapterPosition());
                MyAdapter.calculateCalories(diaries);

            }
        }).attachToRecyclerView(mRecyclerView);
        MyAdapter.calculateCalories(diaries);

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
        switch (item.getItemId()) {
            case R.id.add_food:
                startActivityForResult(new Intent(MainActivity.this, FoodListActivity.class), 1);
                return true;
            case R.id.logout:
                startActivity(new Intent(MainActivity.this, AccountLoginActivity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
