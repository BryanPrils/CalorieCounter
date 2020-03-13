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
    public static final String TEXT = "mText";
    private TextView mTextViewUser;
    private EditText mEditTextUserEmail;
    private Button mBtnUpdateUser;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    private String mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mTextViewUser = findViewById(R.id.tv_username);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mEditTextUserEmail = findViewById(R.id.edit_user_email);
        mBtnUpdateUser = findViewById(R.id.btn_update_user);

        mBtnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        loadData();

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (TextUtils.isEmpty(mEditTextUserEmail.getText().toString())) {
            Toast.makeText(this, "you have to fill in a email addres", Toast.LENGTH_SHORT).show();
        } else {
            mUser.updateEmail(mEditTextUserEmail.getText().toString());
            editor.putString("mText", mEditTextUserEmail.getText().toString());
            editor.apply();
            mTextViewUser.setText(mEditTextUserEmail.getText().toString());
            mEditTextUserEmail.setText("");

        }

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        mText = (sharedPreferences.getString(TEXT, ""));
        mTextViewUser.setText(mText);

    }
}
