package com.example.caloriecounter.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriecounter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    private TextView textViewUser;
    private EditText editTextUserEmail;
    private EditText editTextUserPassword;
    private Button btnUpdateUser;
    private FirebaseUser user;
    private FirebaseAuth auth;

    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        textViewUser = findViewById(R.id.tv_username);
        user = FirebaseAuth.getInstance().getCurrentUser();
        editTextUserEmail = findViewById(R.id.edit_user_email);
        editTextUserPassword = findViewById(R.id.edit_user_password);
        btnUpdateUser = findViewById(R.id.btn_update_user);

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        auth = FirebaseAuth.getInstance();
        loadData();

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (TextUtils.isEmpty(editTextUserEmail.getText().toString())) {
            Toast.makeText(this, "you have to fill in a email addres", Toast.LENGTH_SHORT).show();
        } else {
            user.updateEmail(editTextUserEmail.getText().toString());
            editor.putString("text", editTextUserEmail.getText().toString());
            editor.apply();
            textViewUser.setText(editTextUserEmail.getText().toString());
            editTextUserEmail.setText("");

        }

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = (sharedPreferences.getString(TEXT, ""));
        textViewUser.setText(text);

    }
}
