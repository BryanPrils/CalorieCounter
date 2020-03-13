package com.example.caloriecounter.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriecounter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import model.Food;

public class NewFoodActivity extends AppCompatActivity {

    static final int PICK_IMAGE_REQUEST = 1;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private EditText mEditTextName;
    private EditText mEditTextDecription;
    private EditText mEditTextCalories;
    private Button mbtnsaveFood;
    private Uri mImageUri;


    private StorageReference mStorageRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference foodRef = db.collection("food");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food);
        mImageView = findViewById(R.id.image_view_new_food);
        mProgressBar = findViewById(R.id.progress_upload);
        mEditTextName = findViewById(R.id.edit_text_name);
        mEditTextDecription = findViewById(R.id.edit_text_Description);
        mEditTextCalories = findViewById(R.id.edit_text_calories);
        mbtnsaveFood = findViewById(R.id.btn_save_food);

        mStorageRef = FirebaseStorage.getInstance().getReference();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_food_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_new_food) {
            openFileChooser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(mImageView);

            mbtnsaveFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveFood();
                }
            });
        }
    }

    private void saveFood() {
        if (mImageUri != null) {
            final String name = mEditTextName.getText().toString();
            final String description = mEditTextDecription.getText().toString();
            final int calories = Integer.parseInt(mEditTextCalories.getText().toString());
            final StorageReference imageRef = mStorageRef.child("images/" + mImageUri.getLastPathSegment());
            final UploadTask uploadTask = imageRef.putFile(mImageUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(NewFoodActivity.this, "Recipe added", Toast.LENGTH_SHORT).show();

                }
            });

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageMetadata snapshotMetadata = taskSnapshot.getMetadata();
                    Task<Uri> downloadUrl = imageRef.getDownloadUrl();
                    downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageReference = uri.toString();
                            foodRef.add(new Food(name, description, calories, imageReference));
                        }
                    });
                }


            });

            finish();
            startActivity(new Intent(this, FoodListActivity.class));
        }
    }

}
