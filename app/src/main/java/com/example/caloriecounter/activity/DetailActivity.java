package com.example.caloriecounter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.caloriecounter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.UserDiary;

public class DetailActivity extends FragmentActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference foodRef = db.collection("food");
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String description = intent.getStringExtra("description");
        String imagePath = intent.getStringExtra("imagePath");
        final String name = intent.getStringExtra("name");
        final int calories = intent.getIntExtra("calories", 0);


        ((TextView) findViewById(R.id.tv_description)).setText(description);
        Picasso.with(this).load(imagePath).into((ImageView) findViewById(R.id.image_view_food));
        ((TextView) findViewById(R.id.tv_name_fragment)).setText(name);

        Button buttonSave = findViewById(R.id.btn_save_food_fragment);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailActivity.this, MainActivity.class);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date today = Calendar.getInstance().getTime();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                CollectionReference userDiaryRef = FirebaseFirestore.getInstance()
                        .collection("userDiary");
                userDiaryRef.add(new UserDiary(user.getEmail(), dateFormat.format(today), name, calories));
                MainActivity.diaries.clear();
                finish();
                startActivity(intent);
            }
        });
    }
}
